package com.futurewise.futurecourses_backend.controller;

import com.futurewise.futurecourses_backend.dto.PurchaseCourseRequest;
import com.futurewise.futurecourses_backend.dto.CompleteModuleRequest;
import com.futurewise.futurecourses_backend.model.UserCourse;
import com.futurewise.futurecourses_backend.model.UserModuleProgress;
import com.futurewise.futurecourses_backend.service.UserCourseService;
import com.futurewise.futurecourses_backend.service.UserModuleProgressService;
import com.futurewise.futurecourses_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user-courses")
public class UserCourseController {
    @Autowired
    private UserCourseService userCourseService;
    @Autowired
    private UserModuleProgressService userModuleProgressService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseCourse(@RequestBody PurchaseCourseRequest request) {
        try {
            if (!request.isPaid()) {
                // En producción, aquí se debe validar el pago real con Stripe u otro gateway antes de permitir la compra
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "El pago no fue validado. Simulación: el campo 'paid' debe ser true."));
            }
            userCourseService.purchaseCourse(request.getUserId(), request.getCourseId());
            return ResponseEntity.ok(Map.of("success", true, "message", "Compra realizada exitosamente"));
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg.contains("ya compró")) {
                return ResponseEntity.status(409).body(Map.of("success", false, "message", "El usuario ya ha comprado este curso previamente."));
            } else if (msg.contains("no encontrado")) {
                return ResponseEntity.status(404).body(Map.of("success", false, "message", "Usuario o curso no encontrado."));
            } else if (msg.contains("no está activo")) {
                return ResponseEntity.status(400).body(Map.of("success", false, "message", "El curso no está activo."));
            } else if (msg.contains("no tiene módulos")) {
                return ResponseEntity.status(400).body(Map.of("success", false, "message", "El curso no tiene módulos disponibles."));
            } else if (msg.contains("CUSTOMER")) {
                return ResponseEntity.status(403).body(Map.of("success", false, "message", "Solo los usuarios con rol CUSTOMER pueden comprar cursos."));
            }
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", msg));
        }
    }

    @PostMapping("/complete-module")
    public ResponseEntity<?> completeModule(@RequestBody CompleteModuleRequest request) {
        try {
            UserModuleProgress ump = userModuleProgressService.completeModule(request.getUserId(), request.getCourseId(), request.getModuleId());
            return ResponseEntity.ok(ump);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/progress/{userCourseId}")
    public List<UserModuleProgress> getProgressByUserCourse(@PathVariable Long userCourseId) {
        return userModuleProgressService.getProgressByUserCourse(userCourseId);
    }

    @GetMapping("/mis-cursos")
    public ResponseEntity<?> getUserCoursesWithModules(HttpServletRequest request) {
        // Extraer el userId del token JWT
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Token no proporcionado"));
        }
        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Token inválido o expirado"));
        }
        String username = jwtUtil.extractUsername(token);
        Long userId = userCourseService.getUserIdByUsername(username);
        if (userId == null) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "Usuario no encontrado"));
        }
        return ResponseEntity.ok(userCourseService.getUserCoursesWithModules(userId));
    }
}
