/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.core;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neo4j.ogm.typeconversion.AttributeConverter;

/**
 *
 * @author chunyap
 */
public class DateConverter implements AttributeConverter<Date, Long>{

    @Override
    public Long toGraphProperty(Date t) {
        if( t == null ){
            return null;
        }
        
        return t.getTime();
    }

    @Override
    public Date toEntityAttribute(Long f) {
        try {
            if( f == null ){
                return null;
            }
            
            Date t = new Date();
            t.setTime(f);
            return t;
        } catch (Exception ex) {
            Logger.getLogger(DateConverter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
