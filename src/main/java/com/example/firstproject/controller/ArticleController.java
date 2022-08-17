package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j //로깅을 위한 어노테이션문법(@)
public class ArticleController {

    @Autowired //의존성 주입(DI) -> 스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create") //post방식으로 전송되었기 때문에
    public String crateArticle(ArticleForm form){
        log.info(form.toString());
//        System.out.println(form.toString()); //실제 서버에서 검증시 기록에 남지도 않고 서버성능에도 악영향 미침 -> 로깅기능으로 대체!

        //1. Dto를 Entity로 변환하기
        Article article = form.toEntity(); //form데이터를 entity(article)로 변환
        log.info(article.toString());
//        System.out.println(article.toString()); //데이터 잘 저장되었는지 확인

        //2. Repository에게 Entity를 DB안에 저장하게 하기
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
//        System.out.println(saved.toString()); //데이터 잘 저장되었는지 확인

        return "";
    }

    @GetMapping("/articles/{id}") //{}표시로 이 안은 변하는 수라는걸 알려줌
    public String show(@PathVariable Long id, Model model){ //@PathVariable Url요청로부터 파라미터로 받아옴
        log.info("id = " + id); //파라미터 잘 들어왔는지 확인

        // 1. id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id).orElse(null); //해당 id값에 해당하는 entity가 없다면 null반환

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        // 3. 보여줄 페이지를 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 Article을 가져온다.
        List<Article> articleEntityList = articleRepository.findAll();

        // 2. 가져온 Article 묶음을 view로 전달
        model.addAttribute("articleList", articleEntityList);

        // 3. view 페이지를 설정
        return "articles/index"; //articles/index.mustache 파일이 뷰페이지로 사용되도록


    }
}
