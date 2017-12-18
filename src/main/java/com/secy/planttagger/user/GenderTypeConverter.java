/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.user;

import org.neo4j.ogm.typeconversion.AttributeConverter;

/**
 *
 * @author chunyap
 */
public class GenderTypeConverter implements AttributeConverter<GenderType, String>{

    @Override
    public String toGraphProperty(GenderType t) {
        switch(t)
        {
            case male:                
                return "M";
            case female:    
                return "F";
            case unknown:
                return "NA";
        }
        
        return "NA";
    }

    @Override
    public GenderType toEntityAttribute(String f) {
        switch(f)
        {
            case "M":                
                return GenderType.male;
            case "F":    
                return GenderType.female;
            case "NA":
                return GenderType.unknown;
        }
        
        return GenderType.unknown;
    }
    
}
