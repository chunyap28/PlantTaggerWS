/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.helper;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author chunyap
 */
public class UrlAndPostParamMatcher implements RequestMatcher{
    
    protected String pattern;
    protected RequestMethod httpMethod;
    protected Map<String, String> postParams;
    
    public UrlAndPostParamMatcher(String pattern, RequestMethod httpMethod, Map<String, String> postParams)
    {        
        this.pattern = pattern;
        this.httpMethod = httpMethod;
        this.postParams = postParams;
    }
    
    @Override
    public boolean matches(HttpServletRequest hsr) {
        if( !hsr.getRequestURI().equals(this.pattern) )
            return false;
        
        if( !hsr.getMethod().equals(httpMethod.toString()) )
            return false;
        
        for(Map.Entry<String, String> entry : this.postParams.entrySet()) 
        {
            String key = entry.getKey();
            String value = entry.getValue();
            
            //todo check key value matches
            String refValue = hsr.getParameter(key);
            if( refValue == null || !refValue.equals(value) )
                return false;
        }
        
        return true;
    }
}
