package com.example.demo.service;

import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private final UserRepository userRepository;

    @Override
    public String save(UserInfo userInfo) {
            userRepository.save(userInfo);
            return "Success";

    }

    @Override
    public Collection<UserInfo> findAll() {
        return userRepository.findAll();
    }

    @Override
    public String deleteBySeq(long seq) {
        userRepository.deleteBySeq(seq);
        return "delete!";
    }

    @Override
    public Optional<UserInfo> findByUserId(String id) {
        return userRepository.findById(id);
    }
}
