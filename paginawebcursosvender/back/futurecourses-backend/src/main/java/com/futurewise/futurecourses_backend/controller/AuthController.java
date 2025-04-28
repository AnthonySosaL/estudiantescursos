package com.futurewise.futurecourses_backend.controller;

import com.futurewise.futurecourses_backend.model.User;
import com.futurewise.futurecourses_backend.service.UserService;
import com.futurewise.futurecourses_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        Optional<User> userOpt = userService.authenticate(username, password);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.isVerified());
            return ResponseEntity.ok(Map.of(
                "token", token,
                "username", user.getUsername(),
                "role", user.getRole(),
                "verified", user.isVerified()
            ));
        }
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }

    @PostMapping("/registeradmin")
    public ResponseEntity<?> registerAdmin(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String email = userData.get("email");
        String password = userData.get("password");
        String firstName = userData.get("firstName");
        String lastName = userData.get("lastName");
        String role = userData.getOrDefault("role", "CUSTOMER");
        if (username == null || email == null || password == null || firstName == null || lastName == null) {
            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
        }
        if (userService.existsByUsername(username)) {
            return ResponseEntity.status(409).body("El usuario ya existe");
        }
        if (userService.existsByEmail(email)) {
            return ResponseEntity.status(409).body("El email ya está registrado");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        user.setVerified(true); // El admin crea usuarios verificados
        userService.createUser(user);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @PostMapping("/registerusuario")
    public ResponseEntity<?> registerUsuario(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String email = userData.get("email");
        String password = userData.get("password");
        String firstName = userData.get("firstName");
        String lastName = userData.get("lastName");
        if (username == null || email == null || password == null || firstName == null || lastName == null) {
            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
        }
        if (userService.existsByUsername(username)) {
            return ResponseEntity.status(409).body("El usuario ya existe");
        }
        if (userService.existsByEmail(email)) {
            return ResponseEntity.status(409).body("El email ya está registrado");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole("CUSTOMER"); // Rol predefinido para usuarios normales
        user.setVerified(false); // No verificado por defecto
        userService.createUser(user);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @PostMapping("/cambiar-clave")
    public ResponseEntity<?> cambiarClave(@RequestBody Map<String, String> body, HttpServletRequest request) {
        // Solo usuario autenticado puede cambiar su propia clave
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Token no proporcionado"));
        }
        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Token inválido o expirado"));
        }
        String username = jwtUtil.extractUsername(token);
        String nuevaClave = body.get("nuevaClave");
        if (nuevaClave == null || nuevaClave.length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "La nueva clave debe tener al menos 6 caracteres"));
        }
        boolean ok = userService.cambiarClavePorUsername(username, nuevaClave);
        if (ok) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Contraseña cambiada correctamente"));
        } else {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "Usuario no encontrado"));
        }
    }
}
