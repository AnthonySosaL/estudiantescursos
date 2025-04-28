package com.futurewise.futurecourses_backend.repository;

import com.futurewise.futurecourses_backend.model.UserModuleProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserModuleProgressRepository extends JpaRepository<UserModuleProgress, Long> {
    List<UserModuleProgress> findByUserCourseId(Long userCourseId);
    List<UserModuleProgress> findByModuleId(Long moduleId);
    UserModuleProgress findByUserCourseIdAndModuleId(Long userCourseId, Long moduleId);
}
