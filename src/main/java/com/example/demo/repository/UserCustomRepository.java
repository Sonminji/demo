package com.example.demo.repository;

import com.example.demo.model.UserInfo;
import java.util.Optional;

public interface UserCustomRepository {

    Optional<UserInfo> findById(String id);
}
