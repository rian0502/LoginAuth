package com.belajar.loginauth.Models;

public class LoginRes {
   private String message;

    public LoginRes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
