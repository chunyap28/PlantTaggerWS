/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.plant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.secy.planttagger.common.fileservice.FileReference;
import com.secy.planttagger.common.fileservice.FileReferenceConverter;
import com.secy.planttagger.core.BaseEntity;
import com.secy.planttagger.core.EntityView;
import com.secy.planttagger.post.entity.Post;
import com.secy.planttagger.user.entity.User;
import java.util.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

/**
 *
 * @author chunyap
 */
@NodeEntity(label = "Plant")
public class Plant extends BaseEntity<Plant>{
    
    @NotEmpty 
    @JsonView(EntityView.List.class)
    protected String name;
    @JsonView(EntityView.List.class)
    protected Date since;
    @Convert(FileReferenceConverter.class)
    @JsonView(EntityView.List.class)
    protected FileReference profileImage;
    
    
    public Plant(){}
    
    public Plant(String name, Date since)
    {
        setUuid(UUID.randomUUID().toString());
        this.name = name;
        this.since = since;
        setCreatedAt(new Date());
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the profileImage
     */
    public FileReference getProfileImage() {
        return profileImage;
    }

    /**
     * @param profileImage the profileImage to set
     */
    public void setProfileImage(FileReference profileImage) {
        this.profileImage = profileImage;
    }
    
    @Relationship(type = "PLANTS", direction = Relationship.INCOMING)
    @JsonIgnore
    private User user;
    
    @Relationship(type = "PLANTS", direction = Relationship.INCOMING)
    @JsonIgnore
    public User getUser()
    {
        return user;
    }
    
    @Relationship(type = "PLANTS", direction = Relationship.INCOMING)
    @JsonIgnore
    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * @return the since
     */
    public Date getSince() {
        return since;
    }

    /**
     * @param since the since to set
     */
    public void setSince(Date since) {
        this.since = since;
    }
    
    @Relationship(type = "BELONGS_TO", direction=Relationship.INCOMING)
    private Set<PlantImage> plantImages = new HashSet<>();
    
    @Relationship(type = "BELONGS_TO", direction=Relationship.INCOMING)
    public Set<PlantImage> getPlantImages()
    {
        return this.plantImages;
    }
    
    @JsonIgnore
    public String getOwner() {
        return this.user.getEmail();
    }
}
