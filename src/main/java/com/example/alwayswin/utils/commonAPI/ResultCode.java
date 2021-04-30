package com.example.alwayswin.utils.commonAPI;

public enum ResultCode implements StatusErrorCode{

    SUCCESS(200,"Operate Successfully."),
    FAILED(500,"Operate Failure."),
    VALIDATE_FAILED(404,"Parameter Validation Failed."),
    UNAUTHORIZED(401,"User Does Not Logged In or Token Has Expired."),
    FORBIDDEN(403,"Permissions Denied.");

    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

}
