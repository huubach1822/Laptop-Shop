package com.example.baitaplon.util;

import com.example.baitaplon.entity.User;

public final class Const {
    public static String HOST_URL = "http://192.168.227.119:8081/";
    public static User user;
    public static User getUser() {
        return user;
    }
    public static void setUser(User user) {
        Const.user = user;
    }
}
