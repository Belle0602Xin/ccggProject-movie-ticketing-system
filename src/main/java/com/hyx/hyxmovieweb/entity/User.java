package com.hyx.hyxmovieweb.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    public String username;
    public String password;
    public String nickname;
    public String gender;
    public String email;
    public String phone;

    public User() {}

    public User(String username, String password, String nickname, String gender, String email, String phone) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

}