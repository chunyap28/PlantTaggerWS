/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.account.entity;

import com.secy.planttagger.core.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.secy.planttagger.user.entity.User;
import java.util.Date;
import com.secy.planttagger.core.PasswordGenerator;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.security.SecureRandom;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author chunyap
 */
@NodeEntity(label = "Account")
public class Account extends BaseEntity<Account>{
    
    @NotEmpty protected String userID;
    @NotEmpty protected String password;

    protected List<String> oldPasswords = new ArrayList<>();
    
    public Account() {   
        setUuid(UUID.randomUUID().toString());
        setCreatedAt(new Date());
    }
    
    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {        
        this.password = password;
    }        
    
    /**
     * @return the oldPasswords
     */
    public List<String> getOldPasswords() {
        return oldPasswords;
    }

    /**
     * @param oldPasswords the oldPasswords to set
     */
    public void setOldPasswords(List<String> oldPasswords) {
        this.oldPasswords = oldPasswords;
    }
    
    @Relationship(type = "HAS_PUBLIC_ACCOUNT", direction = Relationship.INCOMING)
    @JsonIgnore
    private User user;
    
    @Relationship(type = "HAS_PUBLIC_ACCOUNT", direction = Relationship.INCOMING)
    @JsonIgnore
    public User getUser()
    {
        return user;
    }
    
    @Relationship(type = "HAS_PUBLIC_ACCOUNT", direction = Relationship.INCOMING)
    @JsonIgnore
    public void setUser(User user)
    {
        this.user = user;
    }
    
    public void setNewPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        setPassword(passwordEncoder.encode(password));
        
        getOldPasswords().add(getPassword());
    }
    
    public boolean checkPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, getPassword());
    }
    
    public String generatePassword() {
        String randomPW = PasswordGenerator.generate();
        setNewPassword(randomPW);
        
        return randomPW;
    }   
    
    public RefreshToken generateToken(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = new String(Base64.encodeBase64(bytes));
        
        return new RefreshToken(this, token, 30);
    }
}
