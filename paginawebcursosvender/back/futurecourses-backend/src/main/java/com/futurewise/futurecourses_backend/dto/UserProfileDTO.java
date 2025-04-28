package com.futurewise.futurecourses_backend.dto;

import java.util.Date;
import com.futurewise.futurecourses_backend.model.UserProfile;

public class UserProfileDTO {
    public Long id;
    public String username;
    public String email;
    public String firstName;
    public String lastName;
    public String phone;
    public String address;
    public String country;
    public String city;
    public String gender;
    public String photoUrl;
    public Date birthDate;
    public String bio;
    public String occupation;

    public UserProfileDTO() {}

    public UserProfileDTO(UserProfile profile) {
        this.id = profile.getId();
        this.username = profile.getUser().getUsername();
        this.email = profile.getUser().getEmail();
        this.firstName = profile.getUser().getFirstName();
        this.lastName = profile.getUser().getLastName();
        this.phone = profile.getPhone();
        this.address = profile.getAddress();
        this.country = profile.getCountry();
        this.city = profile.getCity();
        this.gender = profile.getGender();
        this.photoUrl = profile.getPhotoUrl();
        this.birthDate = profile.getBirthDate();
        this.bio = profile.getBio();
        this.occupation = profile.getOccupation();
    }
}
