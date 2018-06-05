/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.secy.planttagger.account.repository.AccountRepository;
import com.secy.planttagger.account.entity.Account;
import com.secy.planttagger.common.fileservice.FileObject;
import com.secy.planttagger.common.fileservice.FileReference;
import com.secy.planttagger.common.fileservice.FileService;
import com.secy.planttagger.exception.RetrieveFileException;
import com.secy.planttagger.exception.UserNotFoundException;
import com.secy.planttagger.exception.SaveFileException;
import com.secy.planttagger.user.entity.User;
import java.io.IOException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import com.secy.planttagger.user.repository.UserRepository;

/**
 *
 * @author chunyap
 */
@Service("UserService")
public class UserService implements UserDetailsService{
    
    @Autowired private UserRepository userRepository;
    @Autowired private AccountRepository accountRepository;
    @Autowired private FileService fileService;
    
    public User getById(String uuid)
    {
        return userRepository.findByUuid(uuid);
    }
    
    @Override
    public User loadUserByUsername(String userID)
    {
        Account account = accountRepository.findByUserID(userID);
        if( account == null )
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userID));
        
        return account.getUser();
    }
    
    public User loadUserByFacebookId(String facebookId)
    {
        User user = userRepository.findByFacebookid(facebookId);
        if( user == null )
            throw new UserNotFoundException();
        
        return user;
    }
    
    public FileObject getProfileImage(User user)
    {
        try{            
            return fileService.retrieve(user.getProfileImage());
        }
        catch(IOException e)
        {
            throw new RetrieveFileException();
        }
    }
    
    public boolean uploadProfileImage(User user, MultipartFile file)
    {
        try{
            FileReference fref = new FileReference(user.getName());
            fileService.upload(fref, file.getBytes());
            user.setProfileImage(fref);
            userRepository.save(user);
            return true;
        }
        catch(IOException e)
        {            
            throw new SaveFileException();
        }        
    }
}
