/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.secy.planttagger.comment.entity.Comment;
import com.secy.planttagger.common.fileservice.FileReference;
import com.secy.planttagger.common.fileservice.FileReferenceConverter;
import com.secy.planttagger.core.BaseEntity;
import com.secy.planttagger.core.EntityView;
import com.secy.planttagger.plant.entity.Plant;
import com.secy.planttagger.user.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

/**
 *
 * @author chunyaplim
 */
@NodeEntity(label = "Post")
public class Post extends BaseEntity<Post>{
    
    @NotEmpty 
    @JsonView(EntityView.List.class)
    protected String context;
    
    @Convert(FileReferenceConverter.class)
    @JsonView(EntityView.List.class)
    protected FileReference image;
    
    public Post(){}
    
    public Post(String context){
        setUuid(UUID.randomUUID().toString());
        setCreatedAt(new Date());
        this.context = context;
    }
    
    public void setContext(String context){
        this.context = context;
    }
    
    public String getContext(){
        return this.context;
    }

    public void setImage(FileReference image){
        this.image = image;
    }
    
    public FileReference getImage(){
        return this.image;
    }
    
    @Relationship(type = "ABOUTS", direction = Relationship.OUTGOING)
    @JsonView(EntityView.List.class)
    private Plant plant;
    
    @JsonIgnore
    public void setPlant(Plant plant){
        this.plant = plant;
    }
    
    @JsonIgnore
    public Plant getPlant(){
        return this.plant;
    }
    
    @Relationship(type = "ABOUTS", direction = Relationship.INCOMING)
    @JsonView(EntityView.List.class)
    private List<Comment> comments = new ArrayList<>();
    
    @JsonIgnore
    public void addComment(Comment comment){    
        comment.setPost(this);
        this.comments.add(comment);
    }
    
    @JsonView(EntityView.List.class)
    public List<Comment> getComments(){
        return this.comments;
    }
    
    @Relationship(type = "WRITES", direction = Relationship.INCOMING)
    @JsonView(EntityView.List.class)
    private User user;
    
    @JsonIgnore
    public void setUser(User user){
        this.user = user;
    }
    
    @JsonIgnore
    public User getUser(){
        return this.user;
    }
    
    @JsonIgnore
    public String getOwner() {
        return this.user.getEmail();
    }
}
