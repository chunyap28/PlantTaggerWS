/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.plant.service;

import com.secy.planttagger.exception.*;
import com.secy.planttagger.plant.entity.Plant;
import com.secy.planttagger.plant.repository.PlantRepository;
import com.secy.planttagger.user.entity.User;
import java.io.IOException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.secy.planttagger.common.fileservice.*;
import org.springframework.security.access.prepost.*;

/**
 *
 * @author chunyap
 */
@Service("PlantService")
public class PlantService{
    
    @Autowired private PlantRepository plantRepository;
    @Autowired private FileService fileService;
    
    public Plant addPlant(User user, String plantName, Date since, MultipartFile img)
    {
        try{                        
            //create new plant
            Plant plant = new Plant(plantName, since);
            
            //upload file
            FileReference fref = new FileReference(plantName);
            fileService.upload(fref, img.getBytes());
            plant.setProfileImage(fref);

            //add to user relationship
            plant.setUser(user);
            plantRepository.save(plant);
            return plant;
        }
        catch(IOException e)
        {            
            throw new SaveFileException();
        }
    }

    @PostAuthorize ("returnObject.owner == authentication.name")
    public Plant findByUuid(String uuid)
    {
        Plant plant = plantRepository.findByUuid(uuid);
        if( plant == null ){
            throw new ObjectNotFoundException("Plant");
        }
        
        return plant;
    }
    
    public FileObject getProfileImage(Plant plant)
    {
        try{            
            return fileService.retrieve(plant.getProfileImage());
        }
        catch(IOException e)
        {
            throw new RetrieveFileException();
        }
    }
    
    public Page<Plant> getByUserId(String userId, Pageable page)
    {        
        return plantRepository.findByUserId(userId, page);
    }
    
    public void deletePlant(Plant plant){
        plantRepository.delete(plant);
    }
}
