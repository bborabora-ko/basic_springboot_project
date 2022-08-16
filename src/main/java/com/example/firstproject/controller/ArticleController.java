package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @Autowired //의존성 주입(DI) -> 스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create") //post방식으로 전송되었기 때문에
    public String crateArticle(ArticleForm form){
        System.out.println(form.toString());

        //1. Dto를 Entity로 변환하기
        Article article = form.toEntity(); //form데이터를 entity(article)로 변환
        System.out.println(article.toString()); //데이터 잘 저장되었는지 확인

        //2. Repository에게 Entity를 DB안에 저장하게 하기
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString()); //데이터 잘 저장되었는지 확인

        return "";
    }
}
