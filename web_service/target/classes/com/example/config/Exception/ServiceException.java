package com.example.config.Exception;


public class ServiceException extends RuntimeException {

    private String msg;

    private Integer code;

    public ServiceException() {
        super();
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public ServiceException(String message) {
        super(message);
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }
}
