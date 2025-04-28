package com.futurewise.futurecourses_backend.service;

import com.futurewise.futurecourses_backend.model.UserCourse;
import com.futurewise.futurecourses_backend.model.User;
import com.futurewise.futurecourses_backend.model.Course;
import com.futurewise.futurecourses_backend.repository.UserCourseRepository;
import com.futurewise.futurecourses_backend.repository.UserRepository;
import com.futurewise.futurecourses_backend.repository.CourseRepository;
import com.futurewise.futurecourses_backend.dto.UserCourseWithModulesDTO;
import com.futurewise.futurecourses_backend.dto.ModuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class UserCourseService {
    @Autowired
    private UserCourseRepository userCourseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Optional<UserCourse> findByUserIdAndCourseId(Long userId, Long courseId) {
        return Optional.ofNullable(userCourseRepository.findByUserIdAndCourseId(userId, courseId));
    }

    public UserCourse purchaseCourse(Long userId, Long courseId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (userOpt.isEmpty() || courseOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario o curso no encontrado");
        }
        User user = userOpt.get();
        Course course = courseOpt.get();
        if (!"CUSTOMER".equals(user.getRole())) {
            throw new IllegalArgumentException("Solo los usuarios con rol CUSTOMER pueden comprar cursos");
        }
        if (!"ACTIVE".equalsIgnoreCase(course.getStatus())) {
            throw new IllegalArgumentException("El curso no está activo");
        }
        if (userCourseRepository.findByUserIdAndCourseId(userId, courseId) != null) {
            throw new IllegalArgumentException("El usuario ya compró este curso");
        }
        // Validar que el curso tenga al menos un módulo
        if (course.getId() != null && course.getModules() != null && course.getModules().isEmpty()) {
            throw new IllegalArgumentException("El curso no tiene módulos disponibles");
        }
        UserCourse userCourse = new UserCourse();
        userCourse.setUser(user);
        userCourse.setCourse(course);
        return userCourseRepository.save(userCourse);
    }

    public List<UserCourseWithModulesDTO> getUserCoursesWithModules(Long userId) {
        List<UserCourse> userCourses = userCourseRepository.findByUserId(userId);
        List<UserCourseWithModulesDTO> result = new java.util.ArrayList<>();
        for (UserCourse uc : userCourses) {
            Course c = uc.getCourse();
            List<ModuleDTO> modules = c.getModules().stream().map(ModuleDTO::new).toList();
            result.add(new UserCourseWithModulesDTO(
                uc.getId(),
                c.getId(),
                c.getName(),
                c.getDescription(),
                uc.getPurchaseDate(),
                modules
            ));
        }
        return result;
    }

    public Long getUserIdByUsername(String username) {
        return userRepository.findByUsername(username).map(u -> u.getId()).orElse(null);
    }
}
