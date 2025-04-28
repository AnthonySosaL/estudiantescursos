package com.futurewise.futurecourses_backend.repository;

import com.futurewise.futurecourses_backend.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    List<UserCourse> findByUserId(Long userId);
    List<UserCourse> findByCourseId(Long courseId);
    UserCourse findByUserIdAndCourseId(Long userId, Long courseId);
}
