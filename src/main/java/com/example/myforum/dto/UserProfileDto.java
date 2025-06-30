package com.example.myforum.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserProfileDto {
    @NotBlank
    private String fullName;
    @Email
    private String email;
    private boolean lockComments;
    private boolean lockProfile;

    public UserProfileDto() {
    }

    public UserProfileDto(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLockComments() {
        return lockComments;
    }

    public void setLockComments(boolean lockComments) {
        this.lockComments = lockComments;
    }

    public boolean isLockProfile() {
        return lockProfile;
    }

    public void setLockProfile(boolean lockProfile) {
        this.lockProfile = lockProfile;
    }
}
