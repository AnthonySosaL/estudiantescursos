package com.futurewise.futurecourses_backend.repository;

import com.futurewise.futurecourses_backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByProfessorId(Long professorId);
}
