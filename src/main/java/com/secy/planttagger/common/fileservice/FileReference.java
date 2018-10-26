/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.common.fileservice;

import com.fasterxml.jackson.annotation.JsonView;
import com.secy.planttagger.core.DateConverter;
import com.secy.planttagger.core.EntityView;
import com.secy.planttagger.core.Mapper;
import java.util.Date;
import java.util.UUID;
import org.neo4j.ogm.annotation.typeconversion.Convert;

/**
 *
 * @author chunyap
 */
public class FileReference extends Mapper<FileObject>{
    
    @JsonView(EntityView.List.class)
    protected String uuid;
    @JsonView(EntityView.List.class)
    protected String name;
    @JsonView(EntityView.List.class)
    protected String description;
    @JsonView(EntityView.List.class)
    @Convert(DateConverter.class)
    protected Date createdAt;
    
    public FileReference(){
    }
    
    public FileReference(String name)
    {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.setCreatedAt(new Date());
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
