package com.example.firstproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 다대일 관계 : 해당 댓글 엔티티 여러개가, 하나의 Article에 연관된다.
    @JoinColumn(name = "article_id") // "articleid" 컬럼에 Article의 대표값을 저장!
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

}
