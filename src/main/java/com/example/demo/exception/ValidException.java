package com.example.demo.exception;


import lombok.Getter;

@Getter
public class ValidException extends RuntimeException {
    ErrorCode errorCode;


    public ValidException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

}
