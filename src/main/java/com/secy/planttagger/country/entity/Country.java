/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.country.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.secy.planttagger.core.BaseEntity;
import com.secy.planttagger.core.EntityView;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 *
 * @author chunyaplim
 */
@NodeEntity(label = "Country")
public class Country extends BaseEntity<Country>{
    @NotEmpty 
    @JsonView(EntityView.List.class)
    protected String name;
    
    @NotEmpty 
    @JsonView(EntityView.List.class)
    protected String code;
    
    
    public Country(){}
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
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the name to set
     */
    public void setCode(String code) {
        this.code = code;
    }
}
