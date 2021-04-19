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
    private Integer uid;
    private String url;
    private String description;
    private boolean isThumbnail;
    private Timestamp updateTime;

    public Figure() {
        fid = 0;
        uid = 0;
        url = "";
        description = "";
        isThumbnail = true;
        updateTime = new Timestamp(0);
    }

    public Figure(Integer fid, Integer uid, String url, String description, boolean isThumbnail, Timestamp updateTime) {
        this.fid = fid;
        this.uid = uid;
        this.url = url;
        this.description = description;
        this.isThumbnail = isThumbnail;
        this.updateTime = updateTime;
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
        return updateTime;
    }

    public void setThumbnail(boolean thumbnail) {
        isThumbnail = thumbnail;
    }

    public boolean isThumbnail() {
        return isThumbnail;
    }
}
