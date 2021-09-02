package com.xy.service;

import com.xy.entity.Article;
import com.xy.entity.Article_Comments;
import com.xy.repository.ArticleCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleCommentRepository articleCommentRepository;

    @Override
    public List<Article_Comments> findComments(Long articleNo) {
        Article article = articleService.findArticleByArticleNo(articleNo);

        // Null일 수도 있다.
        List<Article_Comments> comments = article.getComments();

        return comments;
    }

    @Override
    public Long create(Map map) {
        Article_Comments comment = new Article_Comments();

        String articleNo = map.get("articleNo").toString();
        Long no = Long.parseLong(articleNo);
        String memberId = map.get("memberId").toString();
        String content = map.get("content").toString();

        System.out.println("작성자와 내용은: " + memberId + ", " + content);

        Date now = new Date(System.currentTimeMillis());

        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 ss초");

        // 게시글 객체를 먼저 찾는다.
        Article article = articleService.findArticleByArticleNo(no);

        System.out.println("찾은 게시글은: " + article.toString());

        comment.setArticleno(article);
        comment.setId(memberId);
        comment.setContent(content);
        comment.setRegtime(format.format(now));

        System.out.println("저장할 댓글의 정보는: " + comment.toString());
        Article_Comments result = articleCommentRepository.save(comment);

        if (result != null) {
            return result.getNo();
        }
        else {
            return 0L;
        }
    }

    @Override
    public String deleteComment(Long commentNo) {
        articleCommentRepository.deleteById(commentNo);
        return "SUCCESS";
    }

}
