package com.example.demo.repository;

import com.example.demo.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    //delete 직접 커스텀
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_info WHERE seq = :seq", nativeQuery = true)
    void deleteBySeq(long seq);

    @Transactional
    Optional<UserInfo> findById(String id);
}
