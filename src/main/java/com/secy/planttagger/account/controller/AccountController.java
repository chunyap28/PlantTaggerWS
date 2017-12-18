/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.account.controller;

import com.secy.planttagger.common.emailservice.SendEmailService;
import com.secy.planttagger.common.emailservice.EmailObject;
import com.secy.planttagger.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
    
    @Autowired AccountService accountService;
    @Autowired SendEmailService emailService;
    
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ResponseEntity<String> resetPassword(@RequestParam(value="userID") String userID) 
    {
        String newPassword = accountService.resetPassword(userID);
                        
        return ResponseEntity.ok(newPassword);
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseEntity<String> test(@RequestParam(value="userID") String userID) 
    {
        try{
            EmailObject email = new EmailObject();
            email.getTo().add("chun_yaplim@hotmail.com");
            email.setContent("Hi this is a test email");
            email.setSubject("Hello from Plant Tagger");
            
            emailService.sendAsync(email);
            
            return ResponseEntity.ok("email is send");
        }
        catch(Exception e)
        {
            return ResponseEntity.ok(e.getMessage());
        }
    }    
}
