/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.core;

import org.springframework.http.ResponseEntity;
import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
/**
 *
 * @author chunyap
 */
public class PtResponse{
    
    protected Map response;

    public PtResponse() 
    {
        this.response = new HashMap<>();
        
        this.setCode(0);
        this.setMessage(null);
        this.setResult(new HashMap<>());
    }
    
    public PtResponse(String message)
    {
        this();
        setMessage(message);
    }
    
    /**
     * @return the code
     */
    public int getCode() {
        return (int) this.response.get("code");
    }

    /**
     * @param code the code to set
     */
    public final void setCode(int code) {
        this.response.put("code", code);
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.response.get("message").toString();
    }

    /**
     * @param message the message to set
     */
    public final void setMessage(String message) {
        this.response.put("message", message);
    }    
    
    /**
     * @return the result
     */
    public Map getResult() {
        return (Map) this.response.get("result");
    }

    /**
     * @param result the result to set
     */
    public final void setResult(Map result) {
        this.response.put("result", result);
    }
    
    public final void setResult(Mapper result) {
        this.response.put("result", result);
    }
    
    public final void setResult(Object result) {
        this.response.put("result", result);
    }
        
    public final void setPage(Page<BaseEntity> page) {
       this.response.put("result", page.getContent());
       this.response.put("totalElements", page.getTotalElements());
       this.response.put("totalPages", page.getTotalPages());
       this.response.put("page", page.getNumber());
       this.response.put("size", page.getSize());
    }
    
    public final void setPage(Page<BaseEntity> page, String keys) { 
       this.setPage(page);       
    }
    
    /**
     * 
     * @param status
     * @return ResponseEntity<>
     */
    public ResponseEntity<Map> toResponseEntity(HttpStatus status)
    {
        return new ResponseEntity<>(this.response, status);
    }
}