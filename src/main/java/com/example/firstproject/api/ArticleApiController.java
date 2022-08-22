package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //RestAPI 용 컨트롤러, 데이터(json)을 반환
@Slf4j
public class ArticleApiController {

    @Autowired //DI(외부(스프링부트가 만들어놓은 놈)에서 가져온다는 뜻)
    private ArticleRepository articleRepository;

    //GET
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article indexOne(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    //POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto){ //RestAPI에서 json으로 데이터를 던질때에는 @RequestBody 추가해야함
        // @RequestBody : Request의 body에서 받아와라
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){ //RestAPI에서 json으로 데이터를 던질때에는 @RequestBody 추가해야함
        // @RequestBody : Request의 body에서 받아와라
        // ResponseEntity<>로 담아서 보내면 상태코드를 같이 심어서 전달할 수 있다
        // 1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2. 대상 엔티티 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if(target == null || id != article.getId()){
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 4. 업데이트 및 정상 응답(200)
        target.patch(article); //수정할 데이터 중 입력되지 않은 값이 있을 경우 null값으로 수정되게 됨
                               //이를 막기 위해 id로 조회한 수정할 데이터에 새롭게 수정한 데이터만 고치는 patch메소드 사용
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);

    }

//    //PATCH 질문
//    @PatchMapping("/api/articles/{id}")
//    public Article update(@PathVariable Long id, @RequestBody ArticleForm dto){ //RestAPI에서 json으로 데이터를 던질때에는 @RequestBody 추가해야함
//        // @RequestBody : Request의 body에서 받아와라
//        // ResponseEntity<>로 담아서 보내면 상태코드를 같이 심어서 전달할 수 있다
//        // 1. 수정용 엔티티 생성
//        Article article = dto.toEntity();
//        log.info("id: {}, article: {}", id, article.toString());
//
//        // 2. 대상 엔티티 조회
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
//        if(target == null || id != article.getId()){
//            // 400, 잘못된 요청 응답!
//            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
//            return target;
//        }
//
//        // 4. 업데이트 및 정상 응답(200)
//        target.patch(article); //수정할 데이터 중 입력되지 않은 값이 있을 경우 null값으로 수정되게 됨
//        //이를 막기 위해 id로 조회한 수정할 데이터에 새롭게 수정한 데이터만 고치는 patch메소드 사용
//        Article updated = articleRepository.save(target);
//        return updated;
//
//    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){

        // 대상 엔티티 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if(target == null){
            log.info("잘못된 요청!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 대상 삭제
        articleRepository.delete(target);

        // 데이터 반환
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}