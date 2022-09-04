package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity // DB가 해당 객체를 인식 가능 => 해당 클래스로 테이블을 만든다는 의미
@AllArgsConstructor
@ToString
@NoArgsConstructor //디폴트생성자 추가
@Getter
public class Article {

    @Id // 대표값을 지정(entity는 기본적으로 대표값필요. like 주민등록번호)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id 자동생성 어노테이션!
    private Long id;

    @Column // DB의 column으로 인식 가능
    private String title;

    @Column
    private String content;

    public void patch(Article article) { //수정된 데이터가 있는 경우에 그 부분만 고쳐줌
        if(article.title != null){
            this.title = article.title;
        }
        if(article.content != null){
            this.content = article.content;
        }
    }
}