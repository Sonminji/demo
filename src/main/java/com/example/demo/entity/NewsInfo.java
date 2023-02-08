package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name="news_info")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsInfo {

    @Id
    @Column
    private int seq;

    @Column
    private String title;

    @Column(name = "pub_date")
    private String pubDate;

    @Column
    private String link;

    @Column
    private String description;

    @Column
    private int views;

}
