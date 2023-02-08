package com.example.demo.repository;

import com.example.demo.entity.NewsInfo;
import com.example.demo.entity.QNewsInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class APIRepositoryImpl implements APICustomRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public long findNews(String title) {
        QNewsInfo newsInfo = QNewsInfo.newsInfo;

        return queryFactory
                .select(newsInfo.count())
                .from(newsInfo)
                .where(newsInfo.title.eq(title))
                .fetchOne();
    }

//    @Override
//    public void insertNews(NewsInfo searchContent) {
//        QNewsInfo newsInfo = QNewsInfo.newsInfo;
//
//        System.out.println(searchContent.getTitle());
//
//        queryFactory.insert(newsInfo)
//                .columns(newsInfo.title, newsInfo.pubDate, newsInfo.link, newsInfo.description)
//                .values(searchContent.getTitle(), searchContent.getPubDate(), searchContent.getLink(), searchContent.getDescription())
//                .execute();
//
//    }

    @Override
    public List<NewsInfo> getNewsList() {
        QNewsInfo newsInfo = QNewsInfo.newsInfo;

        return queryFactory
                .select(newsInfo)
                .from(newsInfo)
                .fetch();
    }
}
