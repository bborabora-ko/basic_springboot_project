package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository; //Article데이터를 db에서 가져올 수 있기 때문에

    public List<CommentDto> comments(Long articleId) {
//        // 조회: 댓글 목록
//        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 변환: 엔티티 --> DTO
//        List<CommentDto> dtos = new ArrayList<>();
//        for (int i = 0; i < comments.size(); i++) {
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }

        // 반환
        return commentRepository.findByArticleId(articleId).stream().map(comment -> CommentDto.createCommentDto(comment)).collect(Collectors.toList());
    }

    @Transactional // 직접 db를 다루기 때문에 문제가 생기면 rollback될 수 있도록 트랜젝션처리함
    public CommentDto create(Long articleId, CommentDto dto) {
        // 게시글 조회 및 예외발생
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 댓글 엔티티를 DB로 저장
        Comment created = commentRepository.save(comment);

        // DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외발생
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));

        // 댓글 수정
        target.patch(dto);

        // DB로 갱신
        Comment updated = commentRepository.save(target);

        // 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        // 댓글 조회 및 예외발생
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));

        // 댓글을 DB에서 삭제
        commentRepository.delete(target);

        // 삭제된 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}
