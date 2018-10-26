/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.post.service;

import com.secy.planttagger.common.fileservice.FileReference;
import com.secy.planttagger.common.fileservice.FileService;
import com.secy.planttagger.exception.ObjectNotFoundException;
import com.secy.planttagger.exception.RetrieveFileException;
import com.secy.planttagger.plant.entity.Plant;
import com.secy.planttagger.post.entity.Post;
import com.secy.planttagger.post.repository.PostRepository;
import com.secy.planttagger.user.entity.User;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author chunyaplim
 */
@Service("PostService")
public class PostService {
    @Autowired private PostRepository postRepository;
    @Autowired private FileService fileService;
    
    public Post createNew(User user, Plant plant, String context, MultipartFile img){
        try{
            Post post = new Post(context);
            post.setPlant(plant);
            post.setUser(user);
            
            FileReference fref = new FileReference(UUID.randomUUID().toString());
            fileService.upload(fref, img.getBytes());
            post.setImage(fref);
            
            postRepository.save(post);
            return post;
        }catch(IOException e)
        {
            throw new RetrieveFileException();
        }       
    }
    
    public Page<Post> getListByPlant(Plant plant, Pageable page){
        return postRepository.findByPlantId(plant.getUuid(), page);
    }
    
    @PostAuthorize ("returnObject.owner == authentication.name")
    public Post findByUuid(String uuid)
    {
        Post post = postRepository.findByUuid(uuid);
        if( post == null ){
            throw new ObjectNotFoundException("Post");
        }
        
        return post;
    }
    
    public Post updatePost(Post post){
        post.setUpdatedAt(new Date());
        return postRepository.save(post);
    }
    
    public void deletePost(Post post){
        postRepository.delete(post);
    }
}
