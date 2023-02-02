package com.example.demo.authority;

import lombok.Getter;

@Getter
public enum UserAuthority {
    USER("USER"), ADMIN("ADMIN");

    private String name;

    private UserAuthority(String name){
        this.name = name;
    }

}
