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
    private String url;
    private String description;
    private boolean isThumbnail;
    private Timestamp updatedTime;

    public Figure() {
        fid = 0;
        pid = 0;
        url = "https://alwayswin-figures.s3.amazonaws.com/product-figure/default-product-thumbnail.png";
        description = "";
        isThumbnail = true;
        updatedTime = new Timestamp(0);
    }

    public Figure(Integer fid, Integer pid, String url, String description, boolean isThumbnail, Timestamp updatedTime) {
        this.fid = fid;
        this.pid = pid;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }


    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
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
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", isThumbnail=" + isThumbnail +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
