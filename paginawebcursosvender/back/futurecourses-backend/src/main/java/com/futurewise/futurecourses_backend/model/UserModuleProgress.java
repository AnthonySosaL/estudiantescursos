package com.futurewise.futurecourses_backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_module_progress")
public class UserModuleProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_course_id", nullable = false)
    private UserCourse userCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @Column(nullable = false)
    private boolean completed = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserCourse getUserCourse() { return userCourse; }
    public void setUserCourse(UserCourse userCourse) { this.userCourse = userCourse; }
    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public Date getCompletedAt() { return completedAt; }
    public void setCompletedAt(Date completedAt) { this.completedAt = completedAt; }
}