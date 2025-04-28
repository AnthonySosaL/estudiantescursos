package com.futurewise.futurecourses_backend.dto;

import com.futurewise.futurecourses_backend.model.User;
import java.util.Date;

public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private String imageUrl;
    private Double price;
    private String status;
    private Long professorId;
    private String professorName;

    public CourseDTO() {}
    public CourseDTO(com.futurewise.futurecourses_backend.model.Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
        this.createdAt = course.getCreatedAt();
        this.imageUrl = course.getImageUrl();
        this.price = course.getPrice();
        this.status = course.getStatus();
        User prof = course.getProfessor();
        if (prof != null) {
            this.professorId = prof.getId();
            this.professorName = prof.getFirstName() + " " + prof.getLastName();
        }
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }
}