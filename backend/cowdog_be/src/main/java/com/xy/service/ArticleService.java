package com.xy.service;

import com.xy.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public Page<Article> findAll(Pageable pageable);
    public Long create(Map map);
    public Article findArticleByArticleNo(Long articleNo);
    public void deleteArticle(Long articleNo);
    public Long update(Map map);
    public List<Article> findByTagsContains(String keyword);
    public Article findArticlebyWriter(String writer);
}