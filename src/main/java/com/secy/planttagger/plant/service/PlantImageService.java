/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.plant.service;

import com.secy.planttagger.common.fileservice.FileObject;
import com.secy.planttagger.common.fileservice.FileReference;
import com.secy.planttagger.common.fileservice.FileService;
import com.secy.planttagger.exception.RetrieveFileException;
import com.secy.planttagger.plant.entity.Plant;
import com.secy.planttagger.plant.entity.PlantImage;
import com.secy.planttagger.plant.repository.PlantImageRepository;
import com.secy.planttagger.plant.repository.PlantRepository;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author chunyap
 */
@Service("PlantImageService")
public class PlantImageService {
    
    @Autowired private PlantRepository plantRepository;
    @Autowired private PlantImageRepository plantImageRepository;
    @Autowired private FileService fileService;
    
    public PlantImage addPlantImage(Plant plant, MultipartFile img){
        try{
            FileReference fref = new FileReference(UUID.randomUUID().toString());
            fileService.upload(fref, img.getBytes());
            PlantImage plantImage = new PlantImage(fref);
            plant.getPlantImages().add(plantImage);
            
            plantRepository.save(plant);
            return plantImage;
        }
        catch(IOException e)
        {
            throw new RetrieveFileException();
        }
    }
    
    public Page<PlantImage> getPlantImages(String plantId, Pageable page){
        return plantImageRepository.findByPlantId(plantId, page);
    }
    
    public FileObject getById(String imageId)
    {
        try{
            PlantImage plantImg = plantImageRepository.findByUuid(imageId);
            return fileService.retrieve(plantImg.getReference());
        }
        catch(IOException e)
        {
            throw new RetrieveFileException();
        }
    }           
}
