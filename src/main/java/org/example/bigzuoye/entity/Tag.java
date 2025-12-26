package org.example.bigzuoye.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Tag {

    private Long id;

    private Long userId;

    private String name;

    private LocalDateTime createdAt;
}
