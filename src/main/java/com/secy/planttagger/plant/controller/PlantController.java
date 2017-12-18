/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.plant.controller;

import com.secy.planttagger.core.PtResponse;
import com.secy.planttagger.plant.entity.Plant;
import com.secy.planttagger.plant.service.PlantService;
import com.secy.planttagger.user.entity.User;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        response.setResult(plant.toMap());
        return response.toResponseEntity(HttpStatus.OK);
    }    
}
