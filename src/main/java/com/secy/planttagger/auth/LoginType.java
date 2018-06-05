/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.auth;

public enum LoginType{ 
    PASSWORD("PASSWORD"),
    FACEBOOK("FACEBOOK"),
    REFRESH("REFRESH"),
    GOOGLE("GOOGLE");  
    
    final private String value;

    LoginType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
