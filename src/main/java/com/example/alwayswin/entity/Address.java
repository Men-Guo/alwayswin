package com.example.alwayswin.entity;

/**
 * @ClassName: Address
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */
public class Address {

    private Integer aid;
    private Integer uid;
    private String name;
    private String phone;
    private String location;
    private String state;
    private String zipCode;

    public Address() {
        aid = 0;
        uid = 0;
        name = "";
        phone = "";
        location = "";
        state = "";
        zipCode = "";
    }

    public Address(Integer aid, Integer uid, String name, String phone,
                   String location, String state, String zipCode) {
        this.aid = aid;
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Integer getAid() {
        return aid;
    }

    public Integer getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return name + " " + phone + " " + location + ", " + state + ", " + zipCode;
    }
}
