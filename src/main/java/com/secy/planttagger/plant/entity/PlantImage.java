/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.plant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secy.planttagger.common.fileservice.FileReference;
import com.secy.planttagger.common.fileservice.FileReferenceConverter;
import com.secy.planttagger.core.BaseEntity;
import java.util.Date;
import java.util.UUID;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

/**
 *
 * @author chunyap
 */
@NodeEntity(label = "PlantImage")
public class PlantImage extends BaseEntity<Plant>{
    
    @Convert(FileReferenceConverter.class)
    protected FileReference image;

    public PlantImage(){}
    
    public PlantImage(FileReference fileReference){
        this.uuid = UUID.randomUUID().toString();
        this.image = fileReference;
        setCreatedAt(new Date());
    }

    /**
     * @return the image
     */
    public FileReference getImage() {
        return image;
    }

    /**
     * @param image the profileImage to set
     */
    public void setImage(FileReference image) {
        this.image = image;
    }
    
    @Relationship(type = "BELONGS_TO", direction = Relationship.OUTGOING)
    @JsonIgnore
    private Plant plant;
    
    @JsonIgnore
    public void setPlant(Plant plant){
        this.plant = plant;
    }
    
    @JsonIgnore
    public Plant getPlant(){
        return this.plant;
    }
}
