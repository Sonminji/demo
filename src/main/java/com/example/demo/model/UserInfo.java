package com.example.demo.model;

import lombok.*;

import javax.persistence.*;


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

    @Builder
    public UserInfo(String id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public UserInfo() {

    }
}
