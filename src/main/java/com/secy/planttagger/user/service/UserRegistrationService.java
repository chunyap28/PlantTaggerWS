/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.secy.planttagger.friend.repository.FriendRepository;
import com.secy.planttagger.facebookclient.PlantTaggerFacebookClient;
import com.secy.planttagger.user.GenderType;
import com.secy.planttagger.user.RegistrationType;
import com.secy.planttagger.user.entity.User;
import com.secy.planttagger.exception.UserNotFoundException;
import com.secy.planttagger.exception.UserExistsException;
import com.secy.planttagger.user.repository.UserRepository;

/**
 *
 * @author chunyap
 */
@Service("UserRegistrationService")
public class UserRegistrationService {
    
    @Autowired private UserRepository userRepository;
        
    public User registerViaEmail(String name, String email, String password)
    {
        User user  = new User(name, email);
        return createUser(user, password);        
    }
   
    public User registerViaFacebook(String token)
    {
        if( !PlantTaggerFacebookClient.validateUserToken(token) )
            throw new UserNotFoundException();
        
        org.springframework.social.facebook.api.User fbUser = PlantTaggerFacebookClient.fetchUserInformation(token);
        
        //get name, email
        System.out.format(fbUser.toString());
        
        User user = new User(fbUser.getName(), fbUser.getEmail());
        user.setFacebookid(fbUser.getId());        
        user.setGender(GenderType.male);
        user.setProfileImage(PlantTaggerFacebookClient.fetchUserProfileImage(token));
        
        return createUser(user, null);        
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
