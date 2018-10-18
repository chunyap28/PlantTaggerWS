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
import com.secy.planttagger.plant.service.PlantImageService;
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
import com.secy.planttagger.plant.entity.PlantImage;

/**
 *
 * @author chunyap
 */
@RestController
public class PlantController {
    
    @Autowired private PlantService plantService;
    @Autowired private PlantImageService plantImageService;
    
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
        
        Plant plant = plantService.findByUuid(id);
        
        PtResponse response = new PtResponse("Success");
        response.setResult(plant);
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/plant/{id}/profile-image", method = RequestMethod.GET)
    public ResponseEntity<Map> getProfileImageById(
            @PathVariable("id") String id
    )
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Plant plant = plantService.findByUuid(id);
        FileObject img = plantService.getProfileImage(plant);
        
        PtResponse response = new PtResponse("Success");
        response.setResult(img);
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/plant/{id}/image", method = RequestMethod.POST)
    public ResponseEntity<Map> addPlantImage(
            @PathVariable("id") String id,
            @RequestParam(value="img") MultipartFile img){
        
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Plant plant = plantService.findByUuid(id);
        PlantImage plantImg = plantImageService.addPlantImage(plant, img);
        
        PtResponse response = new PtResponse("Success");
        response.setResult(plantImg);
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/plant/{id}/images", method = RequestMethod.GET)
    public ResponseEntity<Map> getPlantImages(
            @PathVariable("id") String id,
            Pageable pageable){
        
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Plant plant = plantService.findByUuid(id);
        Page plantImgs = plantImageService.getPlantImages(id, pageable);

        PtResponse response = new PtResponse("Success");
        response.setPage(plantImgs);
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/plant/{id}/image/{img_id}", method = RequestMethod.GET)
    public ResponseEntity<Map> getPlantImageById(
            @PathVariable("id") String plant_id,
            @PathVariable("img_id") String img_id
    )
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        FileObject img = plantImageService.getById(img_id);
        
        PtResponse response = new PtResponse("Success");
        response.setResult(img);
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/plant/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map> deletePlantById(
            @PathVariable("id") String id
    ){
        Plant plant = plantService.findByUuid(id);
        plantService.deletePlant(plant);
        
        PtResponse response = new PtResponse("Success");
        return response.toResponseEntity(HttpStatus.OK);
    }
}
