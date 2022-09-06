package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //JPA와 연동한 테스트!
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        /* Case 1: 4번 게시글의 모든 댓글 조회*/
        {
            // 입력 데이터 준비
            Long articleId = 4L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ" );
            Comment a = new Comment(1L, article, "Park", "굿 윌 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크 탈출");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력!");
        }

        /* Case 2: 1번 게시글의 모든 댓글 조회*/
        {
            // 입력 데이터 준비
            Long articleId = 1L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(1L, "가가가", "1111");
            List<Comment> expected = Arrays.asList(); //1번 게시글의 댓글이 현재 없는 상태이므로 비어있어야 한다.

            // 검증
            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없다");
        }

        /* 과제 */
        /* Case 3: 9번 게시글의 모든 댓글 조회*/
        {
           // 입력 데이터 준비
            Long articleId = 9L;

            // 실제 수행
            List<Comment> comments= commentRepository.findByArticleId(articleId);

            // 예상하기
            List<Comment> expected = Arrays.asList();
            System.out.println("expected: " + expected.toString());

            // 검증
            assertEquals(expected.toString(), comments.toString(), "9번 글은 없다");
        }

        /* Case 4: 9999번 게시글의 모든 댓글 조회*/
        {
            // 입력 데이터 준비
            Long articleId = 9999L;

            // 실제 수행
            List<Comment> comments= commentRepository.findByArticleId(articleId);

            // 예상하기
            List<Comment> expected = Arrays.asList();
            System.out.println("expected: " + expected.toString());

            // 검증
            assertEquals(expected.toString(), comments.toString(), "9999번 글은 없다");
        }

        /* Case 5: -1번 게시글의 모든 댓글 조회*/
        //노트! 존재하지 않는 아이디의 게시글은 db조회되지 않도록 서비스단에서 처리할 것
        // 입력 데이터 준비
        {
            // 입력 데이터 준비
            Long articleId = -1L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "-1번 글은 불가");
        }

    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1: "Park"의 모든 댓글 조회*/
        {
            // 입력 데이터 준비
            String nickname = "Park";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            Article article_1 = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ" );
            Article article_2 = new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ" );
            Article article_3 = new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ" );
            Comment a = new Comment(1L, article_1, "Park", "굿 윌 헌팅");
            Comment b = new Comment(4L, article_2, "Park", "치킨");
            Comment c = new Comment(7L, article_3, "Park", "조깅");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력");
        }

        /* Case 2: "Kim"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "Kim";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            Article article_1 = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ" );
            Article article_2 = new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ" );
            Article article_3 = new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ" );
            Comment a = new Comment(2L, article_1, "Kim", "아이 엠 샘");
            Comment b = new Comment(5L, article_2, "Kim", "샤브샤브");
            Comment c = new Comment(8L, article_3, "Kim", "유튜브");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "Kim의 모든 댓글을 출력");

        }

        /* Case 3: null의 모든 댓글 조회 */
        //노트! null은 db조회되지 않도록 서비스단에서 처리할 것
        {
            // 입력 데이터 준비
            String nickname = null;

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            // 빈 list가 나올 것
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString());

        }

        /* Case 4: ""의 모든 댓글 조회 */
        //노트! ""은 db조회되지 않도록 서비스단에서 처리할 것
        {
            // 입력 데이터 준비
            String nickname = "";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            // 빈 list가 나올 것
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "\"\"의 모든 댓글 조회");

        }

        /* Case 5: "i"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "i";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNicknameIsContaining(nickname);

            // 예상
            Article article_1 = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ" );
            Article article_2 = new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ" );
            Article article_3 = new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ" );
            Comment a = new Comment(2L, article_1, "Kim", "아이 엠 샘");
            Comment b = new Comment(3L, article_1, "Choi", "쇼생크 탈출");
            Comment c = new Comment(5L, article_2, "Kim", "샤브샤브");
            Comment d = new Comment(6L, article_2, "Choi", "초밥");
            Comment e = new Comment(8L, article_3, "Kim", "유튜브");
            Comment f = new Comment(9L, article_3, "Choi", "독서");
            List<Comment> expected = Arrays.asList(a, b, c, d, e, f);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "i의 모든 댓글 조회");

        }


    }
}