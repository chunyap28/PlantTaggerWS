/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.facebookclient;

//import com.restfb.DefaultFacebookClient;
//import com.restfb.FacebookClient;
//import com.restfb.types.User;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author chunyap
 */
@Component
public class PlantTaggerFacebookClient {
    
    public static String appId;
    public static String appSecret;

    @Value("${facebook.app_id}")
    public void setAppId(String app_id) {
        appId = app_id;
    }
    
    @Value("${facebook.app_secret}")
    public void setAppSecret(String app_secret) {
        appSecret = app_secret;
    }
    
    public static Boolean validateUserToken(String userToken) {        
        String appToken = fetchApplicationAccessToken();
        Facebook facebook = new FacebookTemplate(appToken);
             
        appDetail detail = facebook.restOperations().getForObject("https://graph.facebook.com/debug_token?input_token={userToken}&access_token={appToken}", appDetail.class, userToken, appToken);
        return detail.getData().getIsValid();
    }
    
    public static User fetchUserInformation(String userToken) {
        Facebook facebook = new FacebookTemplate(userToken);

        String [] fields = { "id", "email",  "name", "gender" };
        return facebook.fetchObject("me", User.class, fields);                
    }

    public static byte[] fetchUserProfileImage(String userToken){
        Facebook facebook = new FacebookTemplate(userToken);        
        return facebook.userOperations().getUserProfileImage();        
    }
     
    private static String fetchApplicationAccessToken() {           
        OAuth2Operations oauth = new FacebookConnectionFactory(PlantTaggerFacebookClient.appId, PlantTaggerFacebookClient.appSecret).getOAuthOperations();
        return oauth.authenticateClient().getAccessToken();
    }
    
    public static final class appDetail {
        @JsonProperty("data") 
        private data data;
        
        public data getData()
        {
            return data;
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class data {
        
        @JsonProperty("app_id") private String app_id;
        @JsonProperty("application") private String application;        
        @JsonProperty("user_id") private String user_id;        
        @JsonProperty("expires_at") private Date expires_at;        
        @JsonProperty("is_valid") private Boolean is_valid;
         
        public Boolean getIsValid()
        {
            return is_valid;
        }
    }

    
}