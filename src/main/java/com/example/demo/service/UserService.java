package com.example.demo.service;

import com.example.demo.model.UserInfo;

import java.util.Collection;
import java.util.Optional;


public interface UserService {

    public String save(UserInfo userInfo);

    public Collection<UserInfo> findAll();


    String deleteBySeq(long seq);

    Optional<UserInfo> findByUserId(String id);
}
