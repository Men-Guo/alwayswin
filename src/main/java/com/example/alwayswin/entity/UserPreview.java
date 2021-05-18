package com.example.alwayswin.entity;

/**
 * @ClassName: UserPreview
 * @Description:
 * @Author: SQ
 * @Date: 2021-5-17
 */
public class UserPreview {
    private Integer uid;
    private String username;
    private String role;
    private String portrait;
    private String token;

    public UserPreview() {
        uid= 0;
        username = "";
        role = "user";
        portrait = "https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png";
        token = "";
    }

    public UserPreview(Integer uid, String username, String role, String portrait, String token) {
        this.uid = uid;
        this.username = username;
        this.role = role;
        this.portrait = portrait;
        this.token = token;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
