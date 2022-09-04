package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스는 스프링부트와 연동되어 테스팅된다
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {

        // 예상
        Article a = new Article(1L, "가가가", "1111");
        Article b = new Article(2L, "나나나", "2222");
        Article c = new Article(3L, "다다다", "3333");
        List<Article> expected = new ArrayList<>(Arrays.asList(a, b, c));

        // 실제
        List<Article> articles = articleService.index();

        // 비교
        assertEquals(expected.toString(), articles.toString());

    }


    @Test
    void show_성공__존재하는_id_입력() {
        // 예상
        Long id = 1L;
        Article expected = new Article(id, "가가가", "1111");

        // 실제
        Article article = articleService.show(id);

        // 비교
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    void show_실패__존재하지_않는_id_입력() {
        // 예상
        Long id = -1L;
        Article expected = null;

        // 실제
        Article article = articleService.show(id);

        // 비교
        // 주의! null값은 toString메소드로 호출될 수 없음. 따라서 객체자체로 비교함
        assertEquals(expected, article);

    }

    @Test
    @Transactional // 테스트 메서드에 @Transcatinal을 붙이면
                   // 테스트 종료 후 변경된 데이터를 롤백(처음으로 되돌림)처리
    void create_성공__title과_content만_있는_dto_입력() {
        // 예상
        String title = "라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 실제
        Article article = articleService.create(dto);

        // 비교
        // 주의! null값은 toString메소드로 호출될 수 없음. 따라서 객체자체로 비교함
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패__id가_포함된_dto_입력() {
        // 예상
        String title = "라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;

        // 실제
        Article article = articleService.create(dto);

        // 비교
        // 주의! null값은 toString메소드로 호출될 수 없음. 따라서 객체자체로 비교함
        assertEquals(expected, article);

    }

    /* 과제 */
    @Test
    @Transactional
    void update_성공__존재하는_id와_title_content가_있는_dto_입력(){
        // 예상
        Long id = 3L;
        String title = "update1"; //*수정값
        String content = "테스트1"; //*수정값
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = dto.toEntity();

        // 실제
        Article article = articleService.update(id, dto);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_성공__존재하는_id와_title만_있는_dto_입력(){
        // 예상
        Long id = 1L;
        String title = "update2"; //*수정값
        String content = "1111"; //기존값
        ArticleForm dto = new ArticleForm(id, title, null);
        Article expected = new Article(id, title, content);

        // 실제
        Article article = articleService.update(id, dto);

        // 비교
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void update_실패__존재하지_않는_id의_dto_입력(){
        // 예상
        Long id = -1L; //존재하지 않는 id입력
        String title = "update3";
        String content = "실패 테스트";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // 실제
        Article article = articleService.update(id, dto); //null

        // 비교
        assertEquals(expected, article);

    }

//    @Test
//    @Transactional
//    void update_실패__id만_있는_dto_입력(){
//        /* 1. 기존코드 이용 -> 문제 존재
//         * Article.patch() 사용하여 null인 title과 content를 기존값으로 대체한 후 전송
//         */
//
//        /* 해당 코드 문제점
//         * 1번 기존 title과 content를 다 지워 공백으로 보내는 것(ex.사용자가 둘다 지우기를 원함)
//         * 2번 수정 값을 입력하지 않고 id만 보낼시(ex.수정을 하지 않고 싶을 경우)
//         * 의도가 명확히 다른 1,2번 상황 모두 title, content가 null값으로 보내지고 동일하게 처리된다.
//         * 처리결과: null이 해당 id의 기존 데이터로 대체되어 수정처리됨
//         */
//
//        // 예상
//        Long id = 1L;
//        String title = null;
//        String content = null;
//        ArticleForm dto = new ArticleForm(id, title, content);
//        Article expected = new Article(1L, "가가가", "1111");
//
//
//        // 실제
//        Article article = articleService.update(id, dto);
//
//        // 비교
//        assertEquals(expected.toString(), article.toString());
//
//    }

    @Test
    @Transactional
    void update_실패__id만_있는_dto_입력(){
        /* 2. 수정코드
        * title과 content 모두 null일 경우 null객체 반환하게 하여 에러발생시키기
        * */
        // 예상
        Long id = 1L;
        String title = null;
        String content = null;
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // 실제
        Article article = articleService.update(id, dto);

        // 비교
        assertEquals(expected, article);

    }



    @Test
    @Transactional
    void delete_성공__존재하는_id_입력(){
        // 예상

        // 실제

        // 비교

    }

    @Test
    @Transactional
    void delete_실패__존재하지_않는_id_입력(){
        // 예상

        // 실제

        // 비교

    }

}