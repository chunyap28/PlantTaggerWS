/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.common.fileservice;

import com.secy.planttagger.core.Mapper;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author chunyap
 */
public class FileReference extends Mapper<FileObject>{
    
    protected String uuid;
    protected String name;
    protected String description;
    protected Long createdAt;
    
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
