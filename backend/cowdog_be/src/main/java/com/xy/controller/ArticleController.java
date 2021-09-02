package com.xy.controller;

import com.xy.S3.S3SServiceImpl;
import com.xy.common.response.BaseResponseBody;
import com.xy.entity.Article;
import com.xy.entity.Image;
import com.xy.service.ArticleService;
import com.xy.service.ImageService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/cowdog/appeal")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    S3SServiceImpl s3sSer;

    @Autowired
    ImageService imgaeSer;

    Pageable myPageable = PageRequest.of(0, 7, Sort.by(Sort.Direction.DESC, "articleNo"));

    @GetMapping("")
    public Page<Article> findAll(@RequestParam("page") int page, @RequestParam("size") int size, Pageable pageable) {
        // page 넘버와 size를 변수로 받아야 한다. @RequestParam 필요...
        pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"articleNo"));
        Page<Article> list = articleService.findAll(pageable);
        return list;
    }

    @PostMapping(value="/ImgaeUpload", headers = ("content-type=multipart/*"))
    public void execWrite(Image files) throws IOException {
        System.out.println("여기는 이미지 업로드");
        System.out.println(files.toString());
      }
    
    @PostMapping("/imageListImgaeUpload")
    public void execWrite(Image image, MultipartFile files,String userId) throws IOException {
        System.out.println("여기는 게시판 이미지 업로드");
        System.out.println(image.toString());
        System.out.println(files);
        System.out.println(userId);
        // for (int i = 0; i < files.length; i++) {
        String imgPath = s3sSer.upload(image.getFile_path(), files);
        image.setFile_path("https://" + "d2ukkf315368dk.cloudfront.net" + "/" + imgPath);
        // }
        imgaeSer.ArticleImageUpload(image, userId);
      }

    @PostMapping("/create")
    @Transactional // 트랜잭션 설정
    public ResponseEntity<? extends BaseResponseBody> create(@RequestBody HashMap<String, Object> map) {
        System.out.println(map.toString());
        // 작성된 게시글 번호 또는 0L을 반환한다.
        Long result = articleService.create(map);

        
        if ( result != 0L) {
            // 성공 시 작성된 게시글 번호를 반환
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, Long.toString(result)));
        }
        else {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

    @GetMapping("/detail")
    public Article findArticleByArticleNo(@RequestParam("articleNo") Long articleNo){
        // 프론트로부터 게시글 PK를 받아, 해당 게시글 객체를 반환한다.
        Article result = articleService.findArticleByArticleNo(articleNo);
        return result;
    }

    @DeleteMapping("/delete")
    @Transactional
    public Page<Article> deleteArticle(@RequestParam("articleNo") Long articleNo){
        System.out.println("삭제를 시작합니다. : " + articleNo);
        // 게시글 삭제
        articleService.deleteArticle(articleNo);
        return articleService.findAll(myPageable);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<? extends BaseResponseBody> update(@RequestBody HashMap<String, Object> map) {
        Long result = articleService.update(map);

        if ( result != 0L) {
            // 성공 시 작성된 게시글 번호를 반환
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, Long.toString(result)));
        }
        else {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

    @GetMapping("/search")
    public List<Article> findByTagsContains(@RequestParam("searchKeyword") String keyword) {
        System.out.println("검색 키워드는 {" + keyword + "} 입니다.");
        List<Article> result = articleService.findByTagsContains(keyword);
        return result;
    }



}