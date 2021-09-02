package com.xy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.entity.Article;
import com.xy.entity.Image;
import com.xy.entity.Member;
import com.xy.repository.ArticleReportRepository;
import com.xy.repository.ArticleRepository;
import com.xy.repository.ImageRepository;
import com.xy.repository.MemberRepository;

@Service
public class ImageServiceImpl implements ImageService{

	
	
	@Autowired
	ImageRepository imageRepo;
	@Autowired
	MemberRepository memRepo;
	@Autowired
	ArticleServiceImpl articleSer;
	@Autowired
    ArticleRepository articleRepository;
	
	
	
	@Override
	public int upload(Image image, String userId) {
		System.out.println("이미지 업로드~");
		System.out.println(image.toString());
		Member mem=memRepo.getBymemberid(userId);
		System.out.println(mem.toString());
		mem.setFile_path(image.getFile_path());
		mem.setImgFullPath(image.getImgFullPath());
		mem.setTitle("sdf");
		if(memRepo.save(mem)!=null) {
			return 1;
		}
		
		return 0;
	}
	
	@Override
	public List<Image> getImageList() {
	    return imageRepo.findAll();
	 }

	@Override
	public int ArticleImageUpload(Image image, String userId) {
		
		System.out.println("게시판 이미지 업로드~");
		System.out.println(image.toString());
		Article article=articleSer.findArticlebyWriter(userId);
		System.out.println(article.toString());
		article.setFile_path(image.getFile_path());
		article.setImgFullPath(image.getImgFullPath());
		article.setTitle("sdf");
		if(articleRepository.save(article)!=null) {
			return 1;
		}
		
		return 0;
		
	}
}
