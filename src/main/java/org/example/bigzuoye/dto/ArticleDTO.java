package org.example.bigzuoye.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArticleDTO {

    private Long id;

    private String title;

    private String content;

    private List<Long> tagIds;
}
