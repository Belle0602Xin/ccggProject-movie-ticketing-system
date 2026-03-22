package com.hyx.hyxmovieweb.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "t_customer")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account", unique = true)
    public String username;

    @Column(name = "password")
    public String password;

    @Column(name = "alias")
    public String nickname;

    public String gender;
    public String phone;
    public String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    @Column(name = "salt")
    private String salt = "";

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Column(name = "register_time")
    private Date registerTime = new Date();
}