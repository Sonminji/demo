package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    VAILD_ERROR(400,"MEMBER-ERR-400","Valid ERROR"),
    ;

    private int status;
    private String errorCode;
    private String message;

}
