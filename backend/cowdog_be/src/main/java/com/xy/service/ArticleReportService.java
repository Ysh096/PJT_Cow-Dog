package com.xy.service;
import java.util.List;

import com.xy.api.request.ArticleReportPostReq;
import com.xy.entity.Article;
import com.xy.entity.ArticleReport;

public interface ArticleReportService {
    public String createArticleReport(ArticleReportPostReq request);
    public List<ArticleReport> getArticleReportList();
    public void deleteArticleReport(Long articleReportNo);
    public int deleteReportedArticle(String reportedArticleNo, Long reportedArticleLongNo, Article reportedArticleArticleNo);
}