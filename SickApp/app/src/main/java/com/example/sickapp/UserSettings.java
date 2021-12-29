package com.example.sickapp;

import android.app.Application;

public class UserSettings extends Application {
    public static final String PREFERENCES = "preferences";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private String username, password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
