package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/* 롬복을 통한 생성자 & toString메소드 생성*/
@AllArgsConstructor
@ToString
public class ArticleForm {

    private Long id; //edit.mustache에 id input태그 추가했으므로
    @NotBlank(message = "title 입력바람")
              //@Valid를 이용해 @RequestBody로 들어오는 dto 검증
              //반드시 값 존재, " "안됨, 길이가 0보다 커야함
    private String title;
    @NotEmpty(message = "content 입력바람")
            //반드시 값 존재, " "가능, 길이나 크기가 0보다 커야함
    private String content;
    public Article toEntity() {
        return new Article(id, title, content); //id 필드 추가로 인해 코드 변경
    }
}