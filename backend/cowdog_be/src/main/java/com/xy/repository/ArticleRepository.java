package com.xy.repository;

import com.xy.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    public Article findArticleByArticleNo(Long articleNo);
    public Article findArticleByWriter(String writer);
    public List<Article> findByTagsContains(String keyword);

}
