package com.example.demo.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.demo.authority.UserAuthority;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Date;


@Entity(name="user_info")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    @Id
    @Column(name = "seq")
    private int seq;

    @Column(name = "id", unique = true)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "home_number")
    private String homeNumber;
    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "address")
    private String address;

    @Column
    private UserAuthority authority;

    public UserInfo(String id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }



}
