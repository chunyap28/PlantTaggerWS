/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.comment.service;

import com.secy.planttagger.comment.entity.Comment;
import com.secy.planttagger.comment.repository.CommentRepository;
import com.secy.planttagger.exception.ObjectNotFoundException;
import com.secy.planttagger.post.entity.Post;
import com.secy.planttagger.post.repository.PostRepository;
import com.secy.planttagger.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

/**
 *
 * @author chunyaplim
 */
@Service("CommentService")
public class CommentService {
    @Autowired private CommentRepository commentRepository;
    @Autowired private PostRepository postRepository;
    
    @PostAuthorize ("returnObject.owner == authentication.name")
    public Comment findByUuid(String uuid)
    {
        Comment comment = commentRepository.findByUuid(uuid);
        if( comment == null ){
            throw new ObjectNotFoundException("Comment");
        }
        
        return comment;
    }

    public Comment createComment(User user, Post post, String context){
        Comment comment = new Comment(context);
        comment.setUser(user);
        post.addComment(comment);
        postRepository.save(post);
        return comment;
    }
    
    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }
}
