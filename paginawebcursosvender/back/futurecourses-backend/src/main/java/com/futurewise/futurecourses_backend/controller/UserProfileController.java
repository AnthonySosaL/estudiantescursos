package com.futurewise.futurecourses_backend.controller;

import com.futurewise.futurecourses_backend.dto.UserProfileDTO;
import com.futurewise.futurecourses_backend.model.User;
import com.futurewise.futurecourses_backend.model.UserProfile;
import com.futurewise.futurecourses_backend.service.UserProfileService;
import com.futurewise.futurecourses_backend.service.UserService;
import com.futurewise.futurecourses_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    private Long getUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                Optional<User> user = userService.getByUsername(username);
                return user.map(User::getId).orElse(null);
            }
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        if (userId == null) return ResponseEntity.status(401).body("Token inválido o expirado");
        Optional<UserProfile> profile = userProfileService.getByUserId(userId);
        if (profile.isEmpty()) return ResponseEntity.ok().body(null);
        return ResponseEntity.ok(new UserProfileDTO(profile.get()));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdateProfile(@RequestBody UserProfileDTO profileData, HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        if (userId == null) return ResponseEntity.status(401).body("Token inválido o expirado");
        Optional<User> userOpt = userService.getByUsername(profileData.username);
        if (userOpt.isEmpty()) return ResponseEntity.status(404).body("Usuario no encontrado");
        User user = userOpt.get();
        user.setUsername(profileData.username);
        user.setEmail(profileData.email);
        user.setFirstName(profileData.firstName);
        user.setLastName(profileData.lastName);
        userService.createUser(user); // Guarda cambios en User
        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setPhone(profileData.phone);
        profile.setAddress(profileData.address);
        profile.setCountry(profileData.country);
        profile.setCity(profileData.city);
        profile.setGender(profileData.gender);
        profile.setPhotoUrl(profileData.photoUrl);
        profile.setBirthDate(profileData.birthDate);
        profile.setBio(profileData.bio);
        profile.setOccupation(profileData.occupation);
        UserProfile saved = userProfileService.createOrUpdateProfile(userId, profile);
        return ResponseEntity.ok(new UserProfileDTO(saved));
    }
}
