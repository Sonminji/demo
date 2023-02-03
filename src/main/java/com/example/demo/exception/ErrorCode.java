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

    private int status; // http 상태
    private String errorCode;   // 에러코드
    private String message;     // 에러에 대한 메세지

}
