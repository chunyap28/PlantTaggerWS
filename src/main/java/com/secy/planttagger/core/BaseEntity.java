/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.UUID;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Index;

/**
 *
 * @author chunyap
 */
@NodeEntity
public class BaseEntity<T> extends Mapper<T>{
    
    @GraphId @JsonIgnore protected Long id;
    @Index(unique=true) 
    @JsonView(EntityView.List.class)
    protected String uuid;
    //@JsonView(EntityView.List.class)
    protected Long createdAt;  
    
    public Map<String, Object> toFilteredMap(String... keys)
    {
        Map<String, Object> filteredMap = new HashMap<String, Object>();
        
        for (String key : keys) {
            filteredMap.put(key, this.toMap().getOrDefault(key, null));            
        }
        
        return filteredMap;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the uuid
     */    
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        Date date = new Date();
        date.setTime(createdAt);
        return date;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt.getTime();
    }
}
