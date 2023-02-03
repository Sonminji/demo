package com.example.demo.dto;

import com.example.demo.entity.UserInfo;
import groovy.lang.Delegate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Slf4j
@Getter
@AllArgsConstructor
public class UserDetailsDTO implements UserDetails {

    @Delegate
    private UserInfo userInfo;
    private Collection<? extends GrantedAuthority> authorities;


    // 해당 유저의 권한 목록
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String auth : userInfo.getAuthority().split(",")){
            authorities.add(new SimpleGrantedAuthority(auth));
        }
        return authorities;
    }

    // 비밀번호
    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    // 이이디
    @Override
    public String getUsername() {
        return userInfo.getId();
    }

    // 계정 만료 여부(true : 유효, false : 만료)
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    // 계정 잠김 여부(true : 열림, false : 잠김)
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    // 비밀번호 만료 여부(true : 유효, false : 만료)
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 사용자 활성화 여부(true : 활성화, false : 비활성화)
    @Override
    public boolean isEnabled() {
        return false;
    }
}
