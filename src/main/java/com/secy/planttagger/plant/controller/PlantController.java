/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.plant.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.secy.planttagger.core.EntityView;
import com.secy.planttagger.core.PtResponse;
import com.secy.planttagger.plant.entity.Plant;
import com.secy.planttagger.plant.service.PlantService;
import com.secy.planttagger.user.entity.User;
import java.util.*;
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
import com.secy.planttagger.common.fileservice.FileObject;

/**
 *
 * @author chunyap
 */
@RestController
public class PlantController {
    
    @Autowired private PlantService plantService;
    
    /**
     * Add Plant
     * @param name
     * @param img
     * @param since
     * @return 
     */
    @RequestMapping(value = "/user/plant", method = RequestMethod.POST)
    public ResponseEntity<Map> addPlant(
            @RequestParam(value="name") String name, 
            @RequestParam(value="img") MultipartFile img,
            @RequestParam(value="since") @DateTimeFormat(pattern = "yyyy-MM-dd") Date since
    ) 
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Plant plant = plantService.addPlant(user, name, since, img);
        PtResponse response = new PtResponse("Success");
        response.setResult(plant);
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/plants", method = RequestMethod.GET)
    @JsonView(EntityView.List.class)
    public ResponseEntity<Map> getPlants(
            Pageable pageable
    )
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Page plantList = plantService.getByUserId(user.getUuid(), pageable);
                 
        PtResponse response = new PtResponse("Success");
        response.setPage(plantList);
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/plant/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map> getPlantById(
            @PathVariable("id") String id
    )
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Plant plant = plantService.getById(id);
        
        PtResponse response = new PtResponse("Success");
        response.setResult(plant);
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/plant/{id}/image", method = RequestMethod.GET)
    public ResponseEntity<Map> getPlantImageById(
            @PathVariable("id") String id
    )
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        FileObject img = plantService.getProfileImage(id);
        
        PtResponse response = new PtResponse("Success");
        response.setResult(img);
        return response.toResponseEntity(HttpStatus.OK);
    }
}
