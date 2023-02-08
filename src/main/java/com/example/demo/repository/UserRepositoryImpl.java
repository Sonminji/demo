package com.example.demo.repository;

import com.example.demo.entity.QUserInfo;
import com.example.demo.entity.UserInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserInfo> findById(String id) {

        QUserInfo user = QUserInfo.userInfo;


        return Optional.ofNullable(queryFactory
                .select(user)
                .from(user)
                .where(user.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<UserInfo> login(Map<String, Object> userInfo) {
        QUserInfo user = QUserInfo.userInfo;

        return Optional.ofNullable(queryFactory.select(user)
                .from(user)
                .where(user.id.eq((String)userInfo.get("id")).and(user.password.eq((String)userInfo.get("password"))))
                .fetchOne());

    }

    @Override
    public Optional<UserInfo> findUserByEmail(String email) {

        QUserInfo user = QUserInfo.userInfo;

        return Optional.ofNullable(queryFactory
                .select(user)
                .from(user)
                .where(user.email.eq(email))
                .fetchOne());
    }

    @Override
    public Optional<UserInfo> findSMSUser(String email, String loginType) {
        QUserInfo user = QUserInfo.userInfo;

        return Optional.ofNullable(queryFactory
                .select(user)
                .from(user)
                .where(user.email.eq(email).and(user.loginType.eq(loginType)))
                .fetchOne());
    }


}
