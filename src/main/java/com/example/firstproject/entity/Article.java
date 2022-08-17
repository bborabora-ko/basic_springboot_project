package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity // DB가 해당 객체를 인식 가능
@AllArgsConstructor
@ToString
@NoArgsConstructor //디폴트생성자 추가
@Getter
public class Article {

    @Id // 대표값을 지정(entity는 기본적으로 대표값필요. like 주민등록번호)
    @GeneratedValue // 1,2,3, ... 자동 생성 어노테이션!
    private Long id;

    @Column // DB의 column으로 인식 가능
    private String title;

    @Column
    private String content;

}