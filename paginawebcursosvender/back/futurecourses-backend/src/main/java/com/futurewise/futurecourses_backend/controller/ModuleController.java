package com.futurewise.futurecourses_backend.controller;

import com.futurewise.futurecourses_backend.model.Module;
import com.futurewise.futurecourses_backend.model.Course;
import com.futurewise.futurecourses_backend.service.ModuleService;
import com.futurewise.futurecourses_backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<?> createModule(@RequestBody Module module, @RequestParam Long courseId) {
        Optional<Course> courseOpt = courseService.getCourseById(courseId);
        if (courseOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Curso no válido");
        }
        module.setCourse(courseOpt.get());
        Module saved = moduleService.createModule(module);
        return ResponseEntity.ok(new com.futurewise.futurecourses_backend.dto.ModuleDTO(saved));
    }

    @GetMapping("/course/{courseId}")
    public List<com.futurewise.futurecourses_backend.dto.ModuleDTO> getModulesByCourse(@PathVariable Long courseId) {
        return moduleService.getModulesByCourse(courseId).stream().map(com.futurewise.futurecourses_backend.dto.ModuleDTO::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getModuleById(@PathVariable Long id) {
        return moduleService.getModuleById(id)
                .map(module -> ResponseEntity.ok(new com.futurewise.futurecourses_backend.dto.ModuleDTO(module)))
                .orElse(ResponseEntity.notFound().build());
    }
}
