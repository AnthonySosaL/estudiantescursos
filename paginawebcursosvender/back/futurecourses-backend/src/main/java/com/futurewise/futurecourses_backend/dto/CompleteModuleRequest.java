package com.futurewise.futurecourses_backend.dto;

public class CompleteModuleRequest {
    private Long userId;
    private Long courseId;
    private Long moduleId;
    // Getters y setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }
}
