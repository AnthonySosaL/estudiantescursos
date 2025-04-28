package com.futurewise.futurecourses_backend.dto;

public class ModuleDTO {
    private Long id;
    private String name;
    private String description;
    private Integer moduleOrder;
    private Double percentage;
    private Long courseId;

    public ModuleDTO() {}
    public ModuleDTO(com.futurewise.futurecourses_backend.model.Module module) {
        this.id = module.getId();
        this.name = module.getName();
        this.description = module.getDescription();
        this.moduleOrder = module.getModuleOrder();
        this.percentage = module.getPercentage();
        this.courseId = module.getCourse() != null ? module.getCourse().getId() : null;
    }
    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public void setModuleOrder(Integer moduleOrder) {
        this.moduleOrder = moduleOrder;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}