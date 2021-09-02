package com.xy.service;

import com.xy.entity.Article_Comments;

import java.util.List;
import java.util.Map;

public interface ArticleCommentService {
    Long create(Map map);
    List<Article_Comments> findComments(Long articleNo);
    String deleteComment(Long commentNo);
}
