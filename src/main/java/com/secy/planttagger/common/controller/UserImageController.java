/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.common.controller;

import com.secy.planttagger.common.fileservice.FileObject;
import com.secy.planttagger.common.fileservice.FileReference;
import com.secy.planttagger.common.fileservice.FileService;
import com.secy.planttagger.core.PtResponse;
import com.secy.planttagger.exception.RetrieveFileException;
import com.secy.planttagger.user.entity.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chunyaplim
 */
@RestController
@RequestMapping(value = "/user/image-content")
public class UserImageController {
    
    @Autowired FileService fileServ;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map> getImage(@PathVariable("id") String id) {
        try{
            FileReference ref = new FileReference();
            ref.setUuid(id);

            FileObject img = fileServ.retrieve(ref);      
            //System.out.println("img object: " + img.toJson());
            PtResponse response = new PtResponse("Success");
            response.setResult(img.getContent());
            return response.toResponseEntity(HttpStatus.OK);
        }catch(Exception ex){
            throw new RetrieveFileException();
        }
    }
}
