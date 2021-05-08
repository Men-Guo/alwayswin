package com.example.alwayswin.entity;

import java.sql.Timestamp;

/**
 * @ClassName: WishList
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

// todo: 添加product/preview
public class WishList {
    private Integer wid;
    private Integer uid;
    private Integer pid;
    private Timestamp createTime;

    public WishList() {
        wid = 0;
        uid = 0;
        pid = 0;
        createTime = new Timestamp(0);
    }

    public WishList(Integer wid, Integer uid, Integer pid, Timestamp createTime) {
        this.wid = wid;
        this.uid = uid;
        this.pid = pid;
        this.createTime = createTime;
    }

    public Integer getWid() {
        return wid;
    }

    public Integer getUid() {
        return uid;
    }

    public Integer getPid() {
        return pid;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "wid=" + wid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", createTime=" + createTime +
                '}';
    }
}
