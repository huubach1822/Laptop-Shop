package com.example.baitaplon.request_response;

import androidx.annotation.NonNull;

import com.example.baitaplon.entity.User;

public class DeviceList {
    String firebaseToken;
    User user;

    public DeviceList(String firebaseToken, User user) {
        this.firebaseToken = firebaseToken;
        this.user = user;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @NonNull
    @Override
    public String toString() {
        return user.getName();
    }
}
