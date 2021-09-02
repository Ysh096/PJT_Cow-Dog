package com.xy.service;

import java.util.List;

import com.xy.entity.Image;

public interface ImageService {
	public int upload(Image image,String userId);
	public int ArticleImageUpload(Image image,String userId);
	public List<Image> getImageList();
}

