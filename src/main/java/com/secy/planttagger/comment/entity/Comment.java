/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.secy.planttagger.core.BaseEntity;
import com.secy.planttagger.core.EntityView;
import com.secy.planttagger.post.entity.Post;
import com.secy.planttagger.user.entity.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 *
 * @author chunyaplim
 */
@NodeEntity(label = "Comment")
public class Comment extends BaseEntity<Comment>{
    @NotEmpty 
    @JsonView(EntityView.List.class)
    protected String context;
    
    public Comment(){}
    
    public void setContext(String context){
        this.context = context;
    }
    
    public String getContent(){
        return this.context;
    }
    
    @Relationship(type = "ABOUTS", direction = Relationship.OUTGOING)
    @JsonIgnore
    private Post post;
    
    @JsonIgnore
    public void setPost(Post post){
        this.post = post;
    }
    
    @JsonIgnore
    public Post getPost(){
        return this.post;
    }
    
    @Relationship(type = "WRITES", direction = Relationship.INCOMING)
    @JsonIgnore
    private User user;
    
    @JsonIgnore
    public void setUser(User user){
        this.user = user;
    }
    
    @JsonIgnore
    public User getUser(){
        return this.user;
    }
}
