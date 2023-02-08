package com.example.demo.repository;

import com.example.demo.entity.NewsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface APIRepository extends JpaRepository<NewsInfo, Integer>, APICustomRepository {

    @Override
//    @Query(value = "select count(*) as description from news_info where title = :title", nativeQuery = true)
    long findNews(@Param("title") String title);


    @Query(value = "insert into news_info(title, pub_date, link, description) values(:#{#searchContent.title}, date_format(:#{#searchContent.pubDate}, '%Y-%m-%d %H:%i:%s'), :#{#searchContent.link}, :#{#searchContent.description})", nativeQuery = true)
    void insertNews(@Param("searchContent") NewsInfo searchContent);

    @Override
//    @Query(value = "select ni.seq, ni.title, ni.pub_date as pubDate, ni.link, ni.views from news_info ni", nativeQuery = true)
    List<NewsInfo> getNewsList();
}
