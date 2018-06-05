/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secy.planttagger.core.BaseEntity;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 *
 * @author chunyap
 */
@NodeEntity(label = "RefreshToken")
public class RefreshToken extends BaseEntity<Account>{
    @NotEmpty protected String token;
    protected Long expiredAt;

    public RefreshToken(){
        setUuid(UUID.randomUUID().toString());
        setCreatedAt(new Date());
    }
    
    public RefreshToken(Account account, String token, int expirationInDays){
        setUuid(UUID.randomUUID().toString());
        setCreatedAt(new Date());
        this.account = account;
        this.token = token;
        this.expiredAt = this._getExpirationDate(expirationInDays).getTime();
    }
    
    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the expiredAt
     */
    public Long getExpiredAt() {
        return expiredAt;
    }

    /**
     * @param expiredAt the expiredAt to set
     */
    public void setExpiredAt(Long expiredAt) {
        this.expiredAt = expiredAt;
    }
    
    @Relationship(type = "HAS_TOKEN", direction=Relationship.INCOMING)
    @JsonIgnore
    private Account account;
        
    public Account getAccount()
    {
        return account;
    }
    
    public void setAccount(Account account)
    {
        this.account = account;
    }
    
    private Date _getExpirationDate(int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
