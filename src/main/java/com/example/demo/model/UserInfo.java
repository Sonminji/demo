package com.example.demo.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;


@Data
@Entity(name="user_info")
@Getter
@Setter
public class UserInfo {

    @Id
    @Column(name = "seq")
    private int seq;

    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "phone_number")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNumber;
    @Column(name = "home_number")
    private String homeNumber;
    @NotNull
    @Column(name = "email")
    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+", message = "이메일 형식에 맞게 작성해주세요.")
    private String email;

    @Column(name = "birthdate")
    private String birthdate;

    @Column(name = "address")
    private String address;

    public UserInfo(String id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Builder
    public UserInfo(String id, String name, String password, String phoneNumber, String homeNumber, String email, String birthdate, String address) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.homeNumber = homeNumber;
        this.email = email;
        this.birthdate = birthdate;
        this.address = address;
    }


    public UserInfo() {

    }
}
