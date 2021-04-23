package com.example.alwayswin.common.api;

import org.apache.commons.beanutils.PropertyUtilsBean;

import javax.xml.transform.Result;

public class CommonResult<T> {

    //状态码
    private long code;
    private String message;
    private T data;

    protected CommonResult(){}

    public CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     *
     * Return SUCCESS RESULT
     * @param data data from frontend
     * @param <T> entity type
     * @return return a CommonResult class with success's code ,message and data
     */
    public static <T> CommonResult<T> success(T data){
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),data);
    }

    /**
     *
     * Continue use with function failure(ResultCode.FAILED)
     * @param errorCode SUCCESS instance of enum from ResultCode class
     * @param <T>
     * @return errorCode's code and message, no data return here.
     */
    public static<T> CommonResult<T> failure(StatusErrorCode errorCode){
        return new CommonResult<T>(errorCode.getCode(),errorCode.getMessage(),null);
    }

    /**
     *
     * Return Failure CommonResult
     */
    public static<T> CommonResult<T> failure(){
        return failure(ResultCode.FAILED);
    }

    /**
     * Continue use with function validateFailure(ResultCode.VALIDATE_FAILED);
     * @param errorCode VALIDATE_FAILURE instance of enum from ResultCode class
     * @param <T>
     * @return
     */
    public static<T> CommonResult<T> validateFailure(StatusErrorCode errorCode){
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * Return VALIDATE_FAILED code and message
     * @param <T>
     * @return
     */
    public static<T> CommonResult<T> validateFailure(){
        return validateFailure(ResultCode.VALIDATE_FAILED);
    }

    /**
     * Continue use with function unauthorized(ResultCode.UNAUTHORIZED);
     * @param errorCode
     * @param <T>
     * @return
     */
    public static<T> CommonResult<T> unauthorized(StatusErrorCode errorCode){
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * Return UNAUTHORIZED code and message
     * @param <T>
     * @return
     */
    public static<T> CommonResult<T> unauthorized(){
        return unauthorized(ResultCode.UNAUTHORIZED);
    }

    /**
     * Continue use with function forbidden(ResultCode.FORBIDDEN);
     * @param errorCode
     * @param <T>
     * @return
     */
    public static<T> CommonResult<T> forbidden(StatusErrorCode errorCode){
        return new CommonResult<T>(errorCode.getCode(),errorCode.getMessage(),null);
    }

    /**
     * Return FORBIDDEN code and message
     * @param <T>
     * @return
     */
    public static<T> CommonResult<T> forbidden(){
        return forbidden(ResultCode.FORBIDDEN);
    }
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
