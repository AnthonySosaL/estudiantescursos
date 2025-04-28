package com.futurewise.futurecourses_backend.service;

import com.futurewise.futurecourses_backend.model.UserModuleProgress;
import com.futurewise.futurecourses_backend.model.UserCourse;
import com.futurewise.futurecourses_backend.model.Module;
import com.futurewise.futurecourses_backend.repository.UserModuleProgressRepository;
import com.futurewise.futurecourses_backend.repository.UserCourseRepository;
import com.futurewise.futurecourses_backend.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class UserModuleProgressService {
    @Autowired
    private UserModuleProgressRepository userModuleProgressRepository;
    @Autowired
    private UserCourseRepository userCourseRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    public UserModuleProgress completeModule(Long userId, Long courseId, Long moduleId) {
        UserCourse userCourse = userCourseRepository.findByUserIdAndCourseId(userId, courseId);
        if (userCourse == null) throw new IllegalArgumentException("El usuario no ha comprado el curso");
        Optional<Module> moduleOpt = moduleRepository.findById(moduleId);
        if (moduleOpt.isEmpty()) throw new IllegalArgumentException("Módulo no encontrado");
        Module module = moduleOpt.get();
        // Validar que el módulo pertenezca al curso comprado
        if (!module.getCourse().getId().equals(courseId)) {
            throw new IllegalArgumentException("El módulo no pertenece a este curso");
        }
        // Validar que el usuario sea el dueño del UserCourse
        if (!userCourse.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("No tienes permiso para marcar este módulo");
        }
        // Validar que no se marque dos veces
        UserModuleProgress ump = userModuleProgressRepository.findByUserCourseIdAndModuleId(userCourse.getId(), moduleId);
        if (ump != null && ump.isCompleted()) {
            throw new IllegalArgumentException("Este módulo ya fue completado");
        }
        if (ump == null) {
            ump = new UserModuleProgress();
            ump.setUserCourse(userCourse);
            ump.setModule(module);
        }
        ump.setCompleted(true);
        ump.setCompletedAt(new java.util.Date());
        return userModuleProgressRepository.save(ump);
    }

    public List<UserModuleProgress> getProgressByUserCourse(Long userCourseId) {
        return userModuleProgressRepository.findByUserCourseId(userCourseId);
    }
}
