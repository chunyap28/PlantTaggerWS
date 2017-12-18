/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.auth.controller;

import com.secy.planttagger.auth.JwtTokenUtil;
import com.secy.planttagger.user.entity.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
 
    //@Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    //@Autowired private UserDetailsService userDetailsService;
    
    @RequestMapping(value = "", method = RequestMethod.POST, params={"type=PASSWORD"})
    public ResponseEntity<Map> login(
            @RequestParam(value="userID") String userID,
            @RequestParam(value="password") String password,
            Device device)
    {
        return generateToken(device);     
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST, params={"type=FACEBOOK"})
    public ResponseEntity<Map> loginViaFacebook(Device device)
    {
        return generateToken(device);
    }
    
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<Map> logout()
    {
        final User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        //todo invalidate token
        
        Map<String, Object> map = new HashMap<>();
        map.put("message", "logout success");
        return new ResponseEntity<>(map, HttpStatus.OK); 
    }
    
    private ResponseEntity<Map> generateToken(Device device)
    {
        final User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails, device);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        
        return new ResponseEntity<>(map, HttpStatus.OK); 
    }
}