/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.auth;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import com.secy.planttagger.facebookclient.PlantTaggerFacebookClient;
import com.secy.planttagger.user.service.UserService;
import com.secy.planttagger.helper.UrlAndPostParamMatcher;
import com.secy.planttagger.user.entity.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMethod;

public class FacebookTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
        
    @Autowired private UserService userService;
    
    public FacebookTokenAuthenticationFilter(AuthenticationManager authManager) 
    {
        super(new UrlAndPostParamMatcher("/auth", RequestMethod.POST, getMatcherParamMap()));
        
        setAuthenticationManager(authManager);
    }
    
    private static Map<String, String> getMatcherParamMap()
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("type", LoginType.FACEBOOK.toString());
        return paramMap;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                    throws AuthenticationException {

        String inputToken = request.getParameter("token");
        if( PlantTaggerFacebookClient.validateUserToken(inputToken))
        {
            org.springframework.social.facebook.api.User fbuser = PlantTaggerFacebookClient.fetchUserInformation(inputToken);
            if( fbuser == null )
                throw new AuthenticationServiceException("Cannot fetch facebook user");
                
            User user = this.userService.loadUserByFacebookId(fbuser.getId());
            if( user == null )
                throw new AuthenticationServiceException("User is not registered");
            
            Authentication auth = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
            );
            
            
            return auth;
        }
        
        throw new AuthenticationServiceException("Failed to authenticate");
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