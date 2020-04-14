package com.fzxt.exception;


import com.fzxt.response.StatusCode;


public class CustomerException extends RuntimeException {

    private StatusCode statusCode;

    public CustomerException(StatusCode statusCode) {
        super("异常信息" + statusCode.code + "\t异常信息" + statusCode.errmsg);
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
