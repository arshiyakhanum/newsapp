package com.arshiya.newsapp.login.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String uniqueId;
    private String userName;

    public LoggedInUser(String userId, String displayName) {
        this.uniqueId = userId;
        this.userName = displayName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getUserName() {
        return userName;
    }
}