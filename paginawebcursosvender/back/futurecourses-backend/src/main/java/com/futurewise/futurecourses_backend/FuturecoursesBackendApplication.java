package com.futurewise.futurecourses_backend;

import com.futurewise.futurecourses_backend.model.User;
import com.futurewise.futurecourses_backend.model.UserProfile;
import com.futurewise.futurecourses_backend.service.UserProfileService;
import com.futurewise.futurecourses_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FuturecoursesBackendApplication implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private UserProfileService userProfileService;

    public static void main(String[] args) {
        SpringApplication.run(FuturecoursesBackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Crear admin por defecto si no existe
        String adminUsername = "admin";
        String adminEmail = "admin@email.com";
        if (!userService.existsByUsername(adminUsername)) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setEmail(adminEmail);
            admin.setPassword("admin123");
            admin.setFirstName("Admin");
            admin.setLastName("Principal");
            admin.setRole("ADMIN");
            admin.setVerified(true);
            userService.createUser(admin);
            System.out.println("Usuario admin creado por defecto: admin/admin123");
        }
        // Crear profesor por defecto si no existe
        String profUsername = "profesor";
        String profEmail = "profesor@email.com";
        if (!userService.existsByUsername(profUsername)) {
            User profesor = new User();
            profesor.setUsername(profUsername);
            profesor.setEmail(profEmail);
            profesor.setPassword("profesor123");
            profesor.setFirstName("Profesor");
            profesor.setLastName("Principal");
            profesor.setRole("PROFESSOR");
            profesor.setVerified(true);
            User savedProfesor = userService.createUser(profesor);
            // Crear perfil de profesor por defecto
            UserProfile profProfile = new UserProfile();
            profProfile.setUser(savedProfesor);
            profProfile.setBio("Perfil de profesor por defecto");
            userProfileService.createOrUpdateProfile(savedProfesor.getId(), profProfile);
            System.out.println("Usuario profesor creado por defecto: profesor/profesor123");
        }
    }
}
