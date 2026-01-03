package org.example.bigzuoye.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {

    private Long id;

    private Long userId;

    private String title;

    private String content;

    private String summary;

    private Integer viewCount;

    private Integer likeCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer commentCount;

    private String username;

}