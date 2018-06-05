/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.common.fileservice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.secy.planttagger.common.emailservice.EmailObject;
import com.secy.planttagger.common.emailservice.SendEmailService;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neo4j.ogm.typeconversion.AttributeConverter;

/**
 *
 * @author chunyap
 */
public class FileReferenceConverter implements AttributeConverter<FileReference, String>{

    @Override
    public String toGraphProperty(FileReference t) {
        return t.toJson();
    }

    @Override
    public FileReference toEntityAttribute(String f) {
        try {
            FileReference t = FileReference.fromJson(f, FileReference.class);
            return t;
        } catch (JsonMappingException ex) {
            Logger.getLogger(FileReferenceConverter.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileReferenceConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new FileReference();
    }
}