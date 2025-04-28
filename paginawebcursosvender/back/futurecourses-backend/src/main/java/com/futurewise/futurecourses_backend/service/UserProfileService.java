package com.futurewise.futurecourses_backend.service;

import com.futurewise.futurecourses_backend.model.User;
import com.futurewise.futurecourses_backend.model.UserProfile;
import com.futurewise.futurecourses_backend.repository.UserProfileRepository;
import com.futurewise.futurecourses_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<UserProfile> getByUserId(Long userId) {
        return userProfileRepository.findByUserId(userId);
    }

    public UserProfile createOrUpdateProfile(Long userId, UserProfile profileData) {
        User user = userRepository.findById(userId).orElseThrow();
        Optional<UserProfile> existing = userProfileRepository.findByUserId(userId);
        UserProfile profile = existing.orElse(new UserProfile());
        profile.setUser(user);
        profile.setPhone(profileData.getPhone());
        profile.setAddress(profileData.getAddress());
        profile.setCountry(profileData.getCountry());
        profile.setCity(profileData.getCity());
        profile.setGender(profileData.getGender());
        profile.setPhotoUrl(profileData.getPhotoUrl());
        profile.setBirthDate(profileData.getBirthDate());
        profile.setBio(profileData.getBio());
        profile.setOccupation(profileData.getOccupation());
        return userProfileRepository.save(profile);
    }
}
