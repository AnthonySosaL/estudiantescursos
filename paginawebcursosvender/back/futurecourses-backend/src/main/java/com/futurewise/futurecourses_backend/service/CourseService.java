package com.futurewise.futurecourses_backend.service;

import com.futurewise.futurecourses_backend.model.Course;
import com.futurewise.futurecourses_backend.model.User;
import com.futurewise.futurecourses_backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        if (course.getName() == null || course.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del curso es obligatorio");
        }
        // Validar que no exista un curso con el mismo nombre para el mismo profesor
        List<Course> existing = courseRepository.findByProfessorId(course.getProfessor().getId());
        boolean nameExists = existing.stream().anyMatch(c -> c.getName().equalsIgnoreCase(course.getName()));
        if (nameExists) {
            throw new IllegalArgumentException("Ya existe un curso con ese nombre para este profesor");
        }
        return courseRepository.save(course);
    }

    public List<Course> getCoursesByProfessor(Long professorId) {
        return courseRepository.findByProfessorId(professorId);
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
