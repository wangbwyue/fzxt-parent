package com.fzxt.exception;


import com.fzxt.response.StatusCode;

public class ExceptionCast {

    public static void cast(StatusCode statusCode) {
        throw new CustomerException(statusCode);
    }
}
