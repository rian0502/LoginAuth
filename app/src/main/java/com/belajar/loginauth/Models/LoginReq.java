package com.belajar.loginauth.Models;

import com.google.gson.annotations.SerializedName;

public class LoginReq {
    @SerializedName("username")
    private String password;
    private String username;

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
