package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.demo.authority.UserAuthority;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Date;


@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO {

    private int seq;    // 번호

    private String loginType;

    @NotNull
    @NotEmpty(message = "아이디를 8자 이상 입력해주세요.")
    private String id;  // 아이디
    @NotNull
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;    //이름
    @NotNull
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
            message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.")
    private String password;    //비밀번호

    @NotNull
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNumber; // 휴대폰번호
    private String homeNumber;  // 전화번호
    @NotNull
    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+", message = "이메일 형식에 맞게 입력해주세요.")
    private String email;   // 이메일

    private Date birthdate; // 생년월일

    private String address;     //주소

    private String authority;   //권한


}

