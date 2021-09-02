package com.xy.service;
import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.xy.api.request.ArticleReportPostReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.entity.Article;
import com.xy.entity.ArticleReport;
import com.xy.repository.ArticleReportRepository;
import com.xy.repository.ArticleRepository;

@Service
public class ArticleReportServiceImpl implements ArticleReportService {
	@Autowired
	ArticleReportRepository articleReportRepo;
	ArticleRepository articleRepo;
	
	@PersistenceContext
	EntityManager em;
	@Override
	public String createArticleReport(ArticleReportPostReq request) {
		System.out.println(request);
		ArticleReport newArticleReport = new ArticleReport();
		newArticleReport.setTitle(request.getTitle());
		newArticleReport.setContent(request.getContent());
		Timestamp date_now = new Timestamp(System.currentTimeMillis());
		newArticleReport.setRegtime(date_now);
		newArticleReport.setReportedArticleNo(request.getReportedArticleNo());
		if(articleReportRepo.save(newArticleReport)!=null) {
			return "SUCCESS";
		}
		return "FAIL";
	}

	@Override
	public List<ArticleReport> getArticleReportList() {
	    return articleReportRepo.findAll();
	}
	
	@Override
	public void deleteArticleReport(Long articleReportNo) {
		articleReportRepo.deleteById(articleReportNo);
	}
	
	// 게시글 삭제(게시글 삭제 후 유저 관련 신고도 삭제)
	@Override
	@Transactional
	public int deleteReportedArticle(String reportedArticleNo, Long reportedArticleLongNo, Article reportedArticleArticleNo) {
		articleReportRepo.deleteByReportedArticleNo(reportedArticleNo); // reportedArticleNo와 관련된 articleReport 모두 삭제
		String deleteComments = "delete from Article_Comments m where m.articleno=:articleno";
		Query queryComments = em.createQuery(deleteComments).setParameter("articleno", reportedArticleArticleNo);
		int rowsComments = queryComments.executeUpdate();
		String jpql="delete from Article m where m.articleNo=:articleno";
		Query query = em.createQuery(jpql).setParameter("articleno", reportedArticleLongNo);
		int rows = query.executeUpdate();
		return rows + rowsComments;
	}
}
