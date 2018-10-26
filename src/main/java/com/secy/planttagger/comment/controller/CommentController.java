/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.comment.controller;

import com.secy.planttagger.comment.entity.Comment;
import com.secy.planttagger.comment.service.CommentService;
import com.secy.planttagger.core.PtResponse;
import com.secy.planttagger.post.entity.Post;
import com.secy.planttagger.post.service.PostService;
import com.secy.planttagger.user.entity.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chunyaplim
 */
@RestController
public class CommentController {
    @Autowired private PostService postService;
    @Autowired private CommentService commentService;
    
    @RequestMapping(value = "/user/post/{postId}/comment", method = RequestMethod.POST)
    public ResponseEntity<Map> addComment(
            @PathVariable("postId") String postId,
            @RequestParam(value="context") String context){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Post post = postService.findByUuid(postId);
        Comment comment = commentService.createComment(user, post, context);
        PtResponse response = new PtResponse("Success");
        response.setResult(comment);
        return response.toResponseEntity(HttpStatus.OK);             
    }
    
    @RequestMapping(value = "/user/comment/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<Map> deleteComment(
            @PathVariable("commentId") String commentId){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Comment comment = commentService.findByUuid(commentId);
        commentService.deleteComment(comment);
        PtResponse response = new PtResponse("Success");
        return response.toResponseEntity(HttpStatus.OK);             
    }
}
