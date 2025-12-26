package org.example.bigzuoye.dto;

import lombok.Data;

@Data
public class ArticleQueryDTO {

    private int page = 1;

    private int size = 10;

    private String keyword;
}
