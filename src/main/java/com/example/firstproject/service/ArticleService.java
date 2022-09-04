package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service // 서비스계층 선언(서비스 객체를 스프링부트에 생성)
@Slf4j
public class ArticleService {
    @Autowired //DI
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 수정1-1. 잘못된 요청 처리(존재하는 id만 입력한 경우)
        if(article.getTitle() == null & article.getContent() == null){
            log.info("잘못된 요청! id : {}, article{}", id, article.toString());
            return null;
        }

        // 2. 대상 엔티티 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if(target == null || id != article.getId()){
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }

//        /* 수정전 4단계에서의 문제점
//         * 1번 기존 title과 content를 다 지워 공백으로 보내는 것과
//         * 2번 수정 값을 입력하지 않고 id만 보낼시
//         * 의도가 명확히 다른 1,2번 상황 모두 title, content가 null값으로 보내지고 동일하게 처리된다.
//         */
//        // 4. 업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        // 대상 엔티티 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if(target == null){
            log.info("잘못된 요청!");
            return null;
        }
        // 대상 삭제
        articleRepository.delete(target);
        return target;

    }

    // 트랜잭션 테스트를 위한 메소드
    @Transactional // 해당 메소드를 트랜잭션으로 묶는다! -> 해당 메소드가 실패하면 이전상태로 롤백한다!
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

//        // 1의 코드를 for문으로 작성할 경우>
//        List<Article> articleList = new ArrayList<>();
//        for (int i = 0; i < dtos.size(); i++) {
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }

        // 2. entity 묶음을 db로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

//        // <2의 코드를 for문으로 작성할 경우>
//        for (int i = 0; i < articleList.size(); i++) {
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }

        // 3. 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("실패!")
        );

        // 4. 결과값 반환
        return articleList;
    }
}
