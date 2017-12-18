/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import com.secy.planttagger.helper.UrlAndPostParamMatcher;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;


public class PasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
        
    public PasswordAuthenticationFilter(AuthenticationManager authManager) 
    {
        super(new UrlAndPostParamMatcher("/auth", RequestMethod.POST, getMatcherParamMap()));
        
        setAuthenticationManager(authManager);
    }
    
    private static Map<String, String> getMatcherParamMap()
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("type", LoginType.PASSWORD.toString());
        return paramMap;
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest req, HttpServletResponse res)
        throws AuthenticationException, IOException, ServletException {
      
        String userID = req.getParameter("userID");
        String password = req.getParameter("password");
    
        return getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(
                userID,
                password,
                Collections.emptyList()
            )
        );
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest req,
        HttpServletResponse res, FilterChain chain,
        Authentication auth) throws IOException, ServletException {
    
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(req, res);
    }
}
