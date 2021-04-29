package com.example.alwayswin.entity;

import java.sql.Timestamp;

/**
 * @ClassName: Figure
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */
public class Figure {
    private Integer fid;
    private Integer pid;
    private Integer uid;
    private String url;
    private String description;
    private boolean isThumbnail;
    private Timestamp updatedTime;

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Figure() {
        pid =0;
        fid = 0;
        uid = 0;
        url = "";
        description = "";
        isThumbnail = true;
        updatedTime = new Timestamp(0);
    }

    public Figure(Integer fid, Integer pid, Integer uid, String url, String description, boolean isThumbnail, Timestamp updatedTime) {
        this.fid = fid;
        this.pid = pid;
        this.uid = uid;
        this.url = url;
        this.description = description;
        this.isThumbnail = isThumbnail;
        this.updatedTime = updatedTime;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getUpdateTime() {
        return updatedTime;
    }

    public void setThumbnail(boolean thumbnail) {
        isThumbnail = thumbnail;
    }

    public boolean isThumbnail() {
        return isThumbnail;
    }

    @Override
    public String toString() {
        return "Figure{" +
                "fid=" + fid +
                ", pid=" + pid +
                ", uid=" + uid +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", isThumbnail=" + isThumbnail +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
