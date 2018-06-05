/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.user.service;

import com.secy.planttagger.common.fileservice.FileReference;
import com.secy.planttagger.common.fileservice.FileService;
import com.secy.planttagger.exception.SaveFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.secy.planttagger.facebookclient.PlantTaggerFacebookClient;
import com.secy.planttagger.user.GenderType;
import com.secy.planttagger.user.entity.User;
import com.secy.planttagger.exception.UserNotFoundException;
import com.secy.planttagger.exception.UserExistsException;
import com.secy.planttagger.user.repository.UserRepository;
import java.io.IOException;

/**
 *
 * @author chunyap
 */
@Service("UserRegistrationService")
public class UserRegistrationService {
    
    @Autowired private UserRepository userRepository;
    @Autowired private FileService fileService;
        
    public User registerViaEmail(String name, String email, String password)
    {
        User user  = new User(name, email);
        return createUser(user, password);        
    }
   
    public User registerViaFacebook(String token)
    {
        if( !PlantTaggerFacebookClient.validateUserToken(token) )
            throw new UserNotFoundException();
        
        try{
            org.springframework.social.facebook.api.User fbUser = PlantTaggerFacebookClient.fetchUserInformation(token);
        
            //get name, email
            System.out.format(fbUser.toString());

            User user = new User(fbUser.getName(), fbUser.getEmail());
            user.setFacebookid(fbUser.getId());        
            user.setGender(GenderType.male);

            FileReference fref = new FileReference(user.getName());
            fileService.upload(fref, PlantTaggerFacebookClient.fetchUserProfileImage(token));
            user.setProfileImage(fref);

            return createUser(user, null);  
        }catch(IOException e){            
            throw new SaveFileException();
        }  
    }
    
    protected User createUser(User user, String password)
    {
        //check unique
        User existingUser = userRepository.findByEmailOrFacebookid(user.getEmail(), user.getFacebookid());
        
        if( existingUser != null )
            throw new UserExistsException();
        
        user.setupPublicAccount(password);
        
        //save user
        userRepository.save(user);
        return user;
    }
}
