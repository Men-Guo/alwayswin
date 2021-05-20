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
    private String protrait;
    private String token;

    public UserPreview() {
        uid= 0;
        username = "";
        role = "user";
        protrait = "https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png";
        token = "";
    }

    public UserPreview(Integer uid, String username, String role, String portrait, String token) {
        this.uid = uid;
        this.username = username;
        this.role = role;
        this.protrait = portrait;
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
        return protrait;
    }

    public void setPortrait(String portrait) {
        this.protrait = portrait;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserPreview{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", portrait='" + protrait + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
