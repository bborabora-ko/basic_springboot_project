package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> { //jpa가 제공하는 repository 활용
    // CrudRepository<관리할 entity타입, id타입>
    // 현재는 Article entity를 CRUD가능한 repository 생성

}
