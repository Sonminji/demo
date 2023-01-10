package com.example.demo.repository;

import com.example.demo.model.QUserInfo;
import com.example.demo.model.UserInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
