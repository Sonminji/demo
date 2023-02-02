package com.example.demo;

//import com.example.demo.entity.QUserInfo;
import com.example.demo.entity.UserInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class jqueryDslTest {

//    QUserInfo user = QUserInfo.userInfo;
//
//    @Resource
//    EntityManager em;
//    @Resource
//    JPAQueryFactory queryFactory;
//    @Test
//    public void searchParam(){
//        UserInfo searchUser = queryFactory
//                .select(user)
//                .from(user)
//                .where(user.id.eq("abc123"))
//                .fetchOne();
//        assertThat(searchUser.getId()).isEqualTo("abc123");
//    }
}
