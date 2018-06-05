package com.secy.planttagger.user.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.secy.planttagger.common.fileservice.FileObject;
import com.secy.planttagger.core.EntityView;
import com.secy.planttagger.user.service.UserRegistrationService;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.secy.planttagger.user.entity.User;
import com.secy.planttagger.user.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.secy.planttagger.validator.ImageFileValidator;
import com.secy.planttagger.helper.ImageFile;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import com.secy.planttagger.core.PtResponse;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired private UserRegistrationService serv;    
    @Autowired private UserService userServ;
    @Autowired private ImageFileValidator imgFileValidator;
    
    @RequestMapping(value = "", method = RequestMethod.POST, params={"type=PASSWORD"})
    public ResponseEntity<Map> registerViaPASSWORD(
            @RequestParam(value="name") String name, 
            @RequestParam(value="email") String email,
            @RequestParam(value="password") String password) 
    {
        User user = serv.registerViaEmail(name, email, password);
        
        if( user == null )
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);        
        
        return new ResponseEntity<>(user.toFilteredMap("uuid", "name", "email"), HttpStatus.OK);
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST, params={"type=FACEBOOK"})
    public ResponseEntity<Map> registerViaFacebook(
        @RequestParam(value="token") String token)
    {
        User user = serv.registerViaFacebook(token);
        
        if( user == null )
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);        
        
        return new ResponseEntity<>(user.toFilteredMap("uuid", "name", "email"), HttpStatus.OK);
    }    
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    @JsonView(EntityView.List.class)
    public ResponseEntity<User> getUser() 
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        
        if( user == null )
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);        
        
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public ResponseEntity<Map> getUserImage() 
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        
        if( user == null )
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);        
        
        FileObject img = userServ.getProfileImage(user);
        PtResponse response = new PtResponse("Success");
        response.setResult(img);
        return response.toResponseEntity(HttpStatus.OK);
    }
    
    @InitBinder("ImageFile")
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(imgFileValidator);
    }
    
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public ResponseEntity<Map> saveUserImage(@Valid @ModelAttribute("ImageFile") ImageFile file,
                                             BindingResult binding) 
    {
        if(binding.hasErrors()){
            return new PtResponse(binding.getFieldErrors().toString()).toResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
    
        userServ.uploadProfileImage(user, file.getImg());
        return new PtResponse("Success").toResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map> getUserById(
            @PathVariable("id") String id) 
    {
        User user = userServ.getById(id);
        
        if( user == null )
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);        
        
        return new ResponseEntity<>(user.toFilteredMap("uuid", "name", "email"), HttpStatus.OK);
    }
}