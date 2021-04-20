package com.example.alwayswin.entity;

import java.sql.Timestamp;

/**
 * @ClassName: User
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */
public class User {
    private Integer uid;
    private String username;
    private String password;
    private String role;
    private boolean status;
    private Timestamp updatedTime;  // last login time, this name is to stay consist with other attributes in db

    public User() {
        uid= 0;
        username = "";
        password = "";
        role = "";
        status = false;
        updatedTime = new Timestamp(0);
    }

    public User(Integer uid, String username, String password, String role, boolean status, Timestamp updatedTime) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.updatedTime = updatedTime;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }
}
