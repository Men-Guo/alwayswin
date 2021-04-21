package com.example.alwayswin.entity;

import java.util.HashMap;

/**
 * @ClassName: ResponseMsg
 * @Description: 请求返回msg
 * @Author: SQ
 * @Date: 2021-4-20
 */

public class ResponseMsg {
    private Integer status;
    private String message;
    private HashMap<String, Object> responseMap = new HashMap<>();

    public ResponseMsg(){}

    public ResponseMsg(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusAndMessage(Integer status, String message){
        this.status = status;
        this.message = message;
    }

    public HashMap<String, Object> getResponseMap() {
        return responseMap;
    }

    @Override
    public String toString() {
        return "ResponseMsg{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", responseMap=" + responseMap +
                '}';
    }
}
