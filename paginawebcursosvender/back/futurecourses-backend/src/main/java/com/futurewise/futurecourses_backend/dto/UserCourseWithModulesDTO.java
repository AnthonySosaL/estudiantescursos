package com.futurewise.futurecourses_backend.dto;
import java.util.List;
import java.util.Date;

public class UserCourseWithModulesDTO {
    private Long userCourseId;
    private Long courseId;
    private String courseName;
    private String courseDescription;
    private Date purchaseDate;
    private List<ModuleDTO> modules;

    public UserCourseWithModulesDTO() {}
    public UserCourseWithModulesDTO(Long userCourseId, Long courseId, String courseName, String courseDescription, Date purchaseDate, List<ModuleDTO> modules) {
        this.userCourseId = userCourseId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.purchaseDate = purchaseDate;
        this.modules = modules;
    }
    // Getters y setters
    public Long getUserCourseId() { return userCourseId; }
    public void setUserCourseId(Long userCourseId) { this.userCourseId = userCourseId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getCourseDescription() { return courseDescription; }
    public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }
    public Date getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(Date purchaseDate) { this.purchaseDate = purchaseDate; }
    public List<ModuleDTO> getModules() { return modules; }
    public void setModules(List<ModuleDTO> modules) { this.modules = modules; }
}
