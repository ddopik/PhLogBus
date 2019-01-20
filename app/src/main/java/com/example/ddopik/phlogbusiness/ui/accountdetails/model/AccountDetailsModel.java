package com.example.ddopik.phlogbusiness.ui.accountdetails.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Business;

public class AccountDetailsModel {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String profileImage;
    private String coverImage;
    private boolean profileImageChanged;
    private boolean coverImageChanged;

    public AccountDetailsModel(Business business) {
        firstName = business.firstName;
        lastName = business.lastName;
        email = business.email;
        phone = business.phone;
        profileImage = business.thumbnail;
        coverImage = business.imageCover;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public boolean isProfileImageChanged() {
        return profileImageChanged;
    }

    public void setProfileImageChanged(boolean profileImageChanged) {
        this.profileImageChanged = profileImageChanged;
    }

    public boolean isCoverImageChanged() {
        return coverImageChanged;
    }

    public void setCoverImageChanged(boolean coverImageChanged) {
        this.coverImageChanged = coverImageChanged;
    }
}
