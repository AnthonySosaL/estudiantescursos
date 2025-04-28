package com.futurewise.futurecourses_backend.dto;

public class PurchaseCourseRequest {
    private Long userId;
    private Long courseId;
    private boolean paid; // Simulación de pago exitoso. En producción, validar con Stripe u otro gateway.
    // Getters y setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
}
