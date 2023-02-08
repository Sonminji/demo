package com.example.demo.dto;

import lombok.*;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsInfoDTO {


    private int seq;
    private String title;   // 기사 제목
    private String pubDate; // 기사 날짜
    private String link;    // 기사 원본 링크
    private String description; //기사 설명
    private int views;  // 조회수
    
}
