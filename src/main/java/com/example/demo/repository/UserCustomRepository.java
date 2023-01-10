package com.example.demo.repository;

import com.example.demo.model.UserInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserCustomRepository {

    Optional<UserInfo> findById(String id);
}
