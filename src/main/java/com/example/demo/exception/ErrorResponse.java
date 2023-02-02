package com.example.demo.exception;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class ErrorResponse {
    private int status;
    private String message;
    private String code;

    private String detail;


    public ErrorResponse(int status, String message, String detail){
        this.status = status;
        this.message = message;
        this.detail = detail;
    }


    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        System.out.println("흑흑");
        System.out.println(message);
    }

    public ErrorResponse() {

    }
}

