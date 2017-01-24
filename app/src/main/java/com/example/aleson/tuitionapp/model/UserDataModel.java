package com.example.aleson.tuitionapp.model;

import com.google.firebase.database.Exclude;

/**
 * Created by Aleson on 1/20/2017.
 */

public class UserDataModel {

    @Exclude
    private String userID;
    private String userName;
    private String userClass;
    private String profileImageUrl;

    public UserDataModel() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
