/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.plant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secy.planttagger.core.BaseEntity;
import com.secy.planttagger.user.entity.User;
import java.util.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 *
 * @author chunyap
 */
@NodeEntity(label = "Plant")
public class Plant extends BaseEntity<Plant>{
    
    @NotEmpty protected String name;
    protected byte[] profileImage;
    protected Date since;
    
    public Plant(String name, Date since, byte[] img)
    {
        this.name = name;
        this.since = since;
        this.profileImage = img;
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
    public byte[] getProfileImage() {
        return profileImage;
    }

    /**
     * @param profileImage the profileImage to set
     */
    public void setProfileImage(byte[] profileImage) {
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
}
