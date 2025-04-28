package com.futurewise.futurecourses_backend.controller;

import com.futurewise.futurecourses_backend.model.Course;
import com.futurewise.futurecourses_backend.model.User;
import com.futurewise.futurecourses_backend.service.CourseService;
import com.futurewise.futurecourses_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestBody Course course, @RequestParam Long professorId) {
        Optional<User> profOpt = userService.getById(professorId);
        if (profOpt.isEmpty() || !"PROFESSOR".equals(profOpt.get().getRole())) {
            return ResponseEntity.badRequest().body("Profesor no válido");
        }
        course.setProfessor(profOpt.get());
        Course saved = courseService.createCourse(course);
        return ResponseEntity.ok(new com.futurewise.futurecourses_backend.dto.CourseDTO(saved));
    }

    @GetMapping("/professor/{professorId}")
    public List<com.futurewise.futurecourses_backend.dto.CourseDTO> getCoursesByProfessor(@PathVariable Long professorId) {
        return courseService.getCoursesByProfessor(professorId).stream().map(com.futurewise.futurecourses_backend.dto.CourseDTO::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(course -> ResponseEntity.ok(new com.futurewise.futurecourses_backend.dto.CourseDTO(course)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<com.futurewise.futurecourses_backend.dto.CourseDTO> getAllCourses() {
        return courseService.getAllCourses().stream().map(com.futurewise.futurecourses_backend.dto.CourseDTO::new).toList();
    }
}
