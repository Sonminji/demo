package com.example.demo.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class jqGridDTO {
    private List<NewsInfoDTO> rows;
    private int total;
    private int records;
    private int page;
}
