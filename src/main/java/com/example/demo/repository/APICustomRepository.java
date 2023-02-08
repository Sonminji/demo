package com.example.demo.repository;

import com.example.demo.entity.NewsInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface APICustomRepository {

    long findNews(@Param("title") String title);
    List<NewsInfo> getNewsList();
}
