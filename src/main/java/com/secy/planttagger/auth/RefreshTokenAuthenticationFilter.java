/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.auth;

import com.secy.planttagger.account.entity.RefreshToken;
import com.secy.planttagger.account.service.AccountService;
import com.secy.planttagger.helper.UrlAndPostParamMatcher;
import com.secy.planttagger.user.entity.User;
import com.secy.planttagger.user.service.UserService;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author chunyap
 */
public class RefreshTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    
    @Autowired AccountService accountService;
    @Autowired UserService userService;
    
    public RefreshTokenAuthenticationFilter(AuthenticationManager authManager) 
    {
        super(new UrlAndPostParamMatcher("/auth", RequestMethod.POST, getMatcherParamMap()));
        
        setAuthenticationManager(authManager);
    }
    
    private static Map<String, String> getMatcherParamMap()
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("type", LoginType.REFRESH.toString());
        return paramMap;
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest req, HttpServletResponse res)
        throws AuthenticationException, IOException, ServletException {
      
        String token = req.getParameter("refreshToken");
    
        RefreshToken refreshToken = accountService.validateToken(token);
        User user = userService.loadUserByUsername(refreshToken.getAccount().getUserID());
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
            );
            
            
        return auth;
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
