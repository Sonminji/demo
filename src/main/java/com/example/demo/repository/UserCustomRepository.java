package com.example.demo.repository;

import com.example.demo.entity.UserInfo;

import java.util.Map;
import java.util.Optional;

public interface UserCustomRepository {

    Optional<UserInfo> findById(String id);

    Optional<UserInfo> login(Map<String, Object> userInfo);
}
