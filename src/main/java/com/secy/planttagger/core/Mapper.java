/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.core;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Mapper<T> {
    
    public Map<String, Object> toMap() {        
        ObjectMapper m = new ObjectMapper();
        Map<String,Object> map = m.convertValue(this,Map.class);
                   
        return map;                
    }
    
    public static <T> T fromMap(Map<String, Object> map, Class<T> valueType)
    {
        ObjectMapper m = new ObjectMapper();
        T object = (T) m.convertValue(map, valueType);
                   
        return object;   
    }
    
    public String toJson()
    {
        ObjectMapper m = new ObjectMapper();
        try{
           return m.writeValueAsString(this);     
        }
        catch(JsonProcessingException e)
        {
            return null;
        }
    }
    
    public static <T> T fromJson(String json, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException 
    {    
        ObjectMapper m = new ObjectMapper();
        return (T) m.readValue(json, valueType);        
    }    
}
