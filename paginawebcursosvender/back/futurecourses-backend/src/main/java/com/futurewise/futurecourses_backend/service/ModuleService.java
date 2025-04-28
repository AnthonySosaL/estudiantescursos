package com.futurewise.futurecourses_backend.service;

import com.futurewise.futurecourses_backend.model.Module;
import com.futurewise.futurecourses_backend.model.Course;
import com.futurewise.futurecourses_backend.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;

    public Module createModule(Module module) {
        // Validar que no exista otro módulo con el mismo order en el mismo curso
        List<Module> existingModules = moduleRepository.findByCourseId(module.getCourse().getId());
        boolean orderExists = existingModules.stream()
            .anyMatch(m -> m.getModuleOrder().equals(module.getModuleOrder()));
        if (orderExists) {
            throw new IllegalArgumentException("Ya existe un módulo con ese orden en este curso");
        }
        return moduleRepository.save(module);
    }

    public List<Module> getModulesByCourse(Long courseId) {
        return moduleRepository.findByCourseId(courseId);
    }

    public Optional<Module> getModuleById(Long id) {
        return moduleRepository.findById(id);
    }
}
