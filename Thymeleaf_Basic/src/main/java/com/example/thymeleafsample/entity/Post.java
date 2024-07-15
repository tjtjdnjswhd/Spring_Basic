package com.example.thymeleafsample.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
