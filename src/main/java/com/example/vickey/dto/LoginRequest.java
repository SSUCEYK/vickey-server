package com.example.vickey.dto;

public class LoginRequest {

    private String uid;
    private String email;
    private String password;

    // 생성자, Getter, Setter
    public LoginRequest() {}

    public LoginRequest(String uid, String email, String password) {
        this.uid = uid;
        this.email = email;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
