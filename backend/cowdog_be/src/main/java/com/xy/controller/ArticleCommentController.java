package com.xy.controller;

import com.xy.common.response.BaseResponseBody;
import com.xy.entity.Article_Comments;
import com.xy.service.ArticleCommentService;
import com.xy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/cowdog/appealComment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ArticleCommentController {

    @Autowired
    ArticleCommentService articleCommentService;

    @Autowired
    MemberService memberService;



    @PostMapping("/create")
    @Transactional
    public ResponseEntity<? extends BaseResponseBody> create(@RequestBody HashMap<String, Object> map) {
        Long result = articleCommentService.create(map);

        if ( result != 0L) {
            // 성공 시 작성된 게시글 번호를 반환
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, Long.toString(result)));
        }
        else {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
        }
    }

    @GetMapping("/findComments")
    public List<Article_Comments> findComments(@RequestParam("articleNo") Long articleNo) {
        System.out.println("댓글 찾을 게시글 번호는 : " + articleNo);

        List<Article_Comments> result = articleCommentService.findComments(articleNo);

        // 각각의 댓글에 대해, 작성자 id가 존재한다. 이를 PK가 아닌 username으로 변경한다.
        for (Article_Comments comment : result){
            comment.setId(memberService.getMemberById(Long.parseLong(comment.getId())).getMemberid());
        }

        if (result.size() == 0) {
            result = new ArrayList<>();
        }
        return result;

    }

    @DeleteMapping("/deleteComment")
    @Transactional
    public String deleteComment(@RequestParam("commentNo") Long commentNo) {
        String result = articleCommentService.deleteComment(commentNo);
        return result;

    }


}
