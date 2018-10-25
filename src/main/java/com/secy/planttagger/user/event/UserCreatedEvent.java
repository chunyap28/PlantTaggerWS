/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.user.event;

import com.secy.planttagger.user.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author chunyaplim
 */
public class UserCreatedEvent extends ApplicationEvent{
    private User user;
 
    public UserCreatedEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}