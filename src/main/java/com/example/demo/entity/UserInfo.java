package com.example.demo.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.demo.authority.UserAuthority;
import com.example.demo.dto.UserInfoDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Date;


@Entity(name="user_info")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo{

    @Id
    @Column(name = "seq")
    private int seq;    // 번호

    @Column(name = "id", unique = true)
    private String id;  // 아이디
    @Column(name = "name")
    private String name;    // 이름
    @Column(name = "password")
    private String password;    // 비밀번호
    @Column(name = "phone_number")
    private String phoneNumber; // 휴대폰번호
    @Column(name = "home_number")
    private String homeNumber;  // 전화번호
    @Column(name = "email")
    private String email;   // 이메일
    @Column(name = "birthdate")
    private Date birthdate; //생년월일
    @Column(name = "address")
    private String address; //주소
    @Column
    private String authority; //권한

    public UserInfo(String id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }



}
