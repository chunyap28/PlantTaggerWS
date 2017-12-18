/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.plant.service;

import com.secy.planttagger.exception.SaveFileException;
import com.secy.planttagger.plant.entity.Plant;
import com.secy.planttagger.plant.repository.PlantRepository;
import com.secy.planttagger.user.entity.User;
import java.io.IOException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author chunyap
 */
@Service("PlantService")
public class PlantService {
    
    @Autowired private PlantRepository plantRepository;
    
    public Plant addPlant(User user, String plantName, Date since, MultipartFile img)
    {
        try{
            //create new plant
            Plant plant = new Plant(plantName, since, img.getBytes());

            //add to user
            user.getPlants().add(plant);    
            plantRepository.save(plant);
            return plant;
        }
        catch(IOException e)
        {            
            throw new SaveFileException();
        }  
    }
    /*
    public Page<Plant> getByUserId(String uuid)
    {
        return plantRepository.findByUserId(uuid, pageable)
    }*/
}
