package com.xy.repository;

import com.xy.entity.Article_Comments;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCommentRepository extends JpaRepository<Article_Comments, Long> {
    @Override
    List<Article_Comments> findAll(Sort sort);
}
