/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.post.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.secy.planttagger.core.EntityView;
import com.secy.planttagger.core.PtResponse;
import com.secy.planttagger.plant.entity.Plant;
import com.secy.planttagger.plant.service.PlantService;
import com.secy.planttagger.post.entity.Post;
import com.secy.planttagger.post.service.PostService;
import com.secy.planttagger.user.entity.User;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author chunyaplim
 */
@RestController
public class PostController {
    @Autowired private PlantService plantService;
    @Autowired private PostService postService;
    
    @RequestMapping(value = "/user/plant/{plantId}/post", method = RequestMethod.POST)
    public ResponseEntity<Map> addPost(
            @PathVariable("plantId") String plantId,
            @RequestParam(value="context") String context, 
            @RequestParam(value="img") MultipartFile img){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Plant plant = plantService.findByUuid(plantId);
        Post post = postService.createNew(user, plant, context, img);
        PtResponse response = new PtResponse("Success");
        response.setResult(post);
        return response.toResponseEntity(HttpStatus.OK);             
    }
    
    @RequestMapping(value = "/user/plant/{plantId}/posts", method = RequestMethod.GET)
    @JsonView(EntityView.List.class)
    public ResponseEntity<Map> getPosts( 
            @PathVariable("plantId") String plantId,
            Pageable pageable ){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Plant plant = plantService.findByUuid(plantId);
        Page posts = postService.getListByPlant(plant, pageable);
                 
        PtResponse response = new PtResponse("Success");
        response.setPage(posts);
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/post/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map> deletePostById(
            @PathVariable("id") String id
    ){
        Post post = postService.findByUuid(id);
        postService.deletePost(post);
        
        PtResponse response = new PtResponse("Success");
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/post/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map> updatePostById(
            @PathVariable("id") String id,
            @RequestParam(value="context") String context
    ){
        Post post = postService.findByUuid(id);
        postService.deletePost(post);
        
        PtResponse response = new PtResponse("Success");
        return response.toResponseEntity(HttpStatus.OK);
    }
}
