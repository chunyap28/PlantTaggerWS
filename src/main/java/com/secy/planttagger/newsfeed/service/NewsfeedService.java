/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.newsfeed.service;

import com.secy.planttagger.plant.entity.Plant;
import com.secy.planttagger.post.entity.Post;
import com.secy.planttagger.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author chunyaplim
 */
@Service("NewsfeedService")
public class NewsfeedService {
    @Autowired private PostRepository postRepository;
    
    //todo based on the user follows
    public Page<Post> getFeeds(Pageable page){
        PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(), new Sort(Sort.Direction.DESC, "CreatedAt"));
        return postRepository.findAll(pageRequest);
    }
}
