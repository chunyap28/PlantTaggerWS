/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.newsfeed.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.secy.planttagger.core.EntityView;
import com.secy.planttagger.core.PtResponse;
import com.secy.planttagger.newsfeed.service.NewsfeedService;
import com.secy.planttagger.user.entity.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chunyaplim
 */
@RestController
public class NewsfeedController {
    @Autowired private NewsfeedService newsfeedService;
    
    @RequestMapping(value = "/user/newsfeeds/testing", method = RequestMethod.GET)
    @JsonView(EntityView.List.class)
    public ResponseEntity<Map> getFeeds2(
            Pageable pageable
    )
    {
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        //Page feeds = newsfeedService.getFeeds(pageable);
                 
        PtResponse response = new PtResponse("Success");
        //response.setPage(feeds);
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/newsfeeds", method = RequestMethod.GET)
    @JsonView(EntityView.List.class)
    public ResponseEntity<Map> getFeeds(
            Pageable pageable
    )
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Page feeds = newsfeedService.getFeeds(pageable);
                 
        PtResponse response = new PtResponse("Success");
        response.setPage(feeds);
        return response.toResponseEntity(HttpStatus.OK);
    }
}
