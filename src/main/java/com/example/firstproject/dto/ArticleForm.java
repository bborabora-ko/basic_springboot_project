package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

/* 롬복을 통한 생성자 & toString메소드 생성*/
@AllArgsConstructor
@ToString
public class ArticleForm {

    private Long id; //edit.mustache에 id input태그 추가했으므로
    private String title;
    private String content;

    public Article toEntity() {
        return new Article(id, title, content);
    } //id 필드 추가로 인해 코드 변경
}