/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.account.service;

import com.secy.planttagger.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.secy.planttagger.account.entity.Account;
import com.secy.planttagger.account.entity.RefreshToken;
import com.secy.planttagger.account.repository.RefreshTokenRepository;
import com.secy.planttagger.exception.UserNotFoundException;

@Service("AccountService")
public class AccountService {
    
    @Autowired private AccountRepository accountRepository;
    @Autowired private RefreshTokenRepository tokenRepository;
    
    public String resetPassword(String userID)
    {
        Account account = accountRepository.findByUserID(userID);
        if( account == null )
            throw new UserNotFoundException();
        
        String newPassword = account.generatePassword();
        
        accountRepository.save(account);
        //test
        boolean stat = account.checkPassword(newPassword);
        return newPassword;
    }
    
    public RefreshToken generateToken(String userID){
        Account account = accountRepository.findByUserID(userID);
        if( account == null ){
            throw new UserNotFoundException();
        }
        
        RefreshToken token = account.generateToken();
        tokenRepository.save(token);
        
        return token;
    }
    
    public RefreshToken validateToken(String token){
        RefreshToken refreshToken = tokenRepository.findByToken(token);
        if( token == null ){
            throw new RuntimeException("Invalid token");
        }
        
        return refreshToken;
    }
}
