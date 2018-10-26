/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.*;
import com.secy.planttagger.core.BaseEntity;

import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Index;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.secy.planttagger.friend.entity.Friend;
import com.secy.planttagger.user.GenderTypeConverter;
import com.secy.planttagger.account.entity.Account;
import com.secy.planttagger.common.fileservice.FileReference;
import com.secy.planttagger.common.fileservice.FileReferenceConverter;
import com.secy.planttagger.core.EntityView;
import com.secy.planttagger.country.entity.Country;
import com.secy.planttagger.plant.entity.Plant;
import com.secy.planttagger.user.GenderType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author chunyap
 */
@NodeEntity(label = "User")
public class User extends BaseEntity<User>
                  implements UserDetails{
               
    @NotEmpty
    @JsonView(EntityView.List.class)
    protected String name;
    @Convert(GenderTypeConverter.class) protected GenderType gender = GenderType.unknown;
    @Convert(FileReferenceConverter.class) protected FileReference profileImage;
    
    @NotEmpty @Email 
    @Index(unique=true) 
    @JsonView(EntityView.List.class)
    protected String email;
    
    
    @Index(unique=true) @JsonIgnore protected String facebookid;
    @Index(unique=true) @JsonIgnore protected String googleid;
    
    @JsonView(EntityView.List.class)
    private String gardenName;

    public User() {}

    public User(String name, String email) {
        setUuid(UUID.randomUUID().toString());
        this.name = name;
        this.email = email;
        this.gardenName = this.name + "'s garden";     
        setCreatedAt(new Date());
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * @return the profileImage
     */
    public FileReference getProfileImage() {
        return profileImage;
    }

    /**
     * @param profileImage the profileImage to set
     */
    public void setProfileImage(FileReference profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * @return the facebookid
     */
    public String getFacebookid() {
        return facebookid;
    }

    /**
     * @param facebookid the facebookid to set
     */
    public void setFacebookid(String facebookid) {
        this.facebookid = facebookid;
    }

    /**
     * @return the googleid
     */
    public String getGoogleid() {
        return googleid;
    }

    /**
     * @param googleid the googleid to set
     */
    public void setGoogleid(String googleid) {
        this.googleid = googleid;
    }
    
    /**
     * @return the gender
     */
    public GenderType getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(GenderType gender) {
        this.gender = gender;
    }
    
    /**
     * @return the gardenName
     */
    public String getGardenName() {
        return gardenName;
    }

    /**
     * @param gardenName the gardenName to set
     */
    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }

    @Relationship(type = "FRIEND", direction = Relationship.UNDIRECTED)
    @JsonIgnore
    private List<Friend> friends = new ArrayList<>();

    public List<Friend> getFriends()
    {
        return this.friends;
    }
    
    public void beFriend(User friend) {
        Friend friendRelationship = new Friend(this, friend, new Date());
    } 
    
    @Relationship(type = "PLANTS", direction = Relationship.OUTGOING)
    @JsonIgnore
    private List<Plant> plants = new ArrayList<>();

    public List<Plant> getPlants()
    {
        return this.plants;
    }
    
    @Relationship(type = "HAS_PUBLIC_ACCOUNT", direction=Relationship.OUTGOING)
    @JsonIgnore
    private Set<Account> publicAccounts = new HashSet<>();
    
    @Relationship(type = "HAS_PUBLIC_ACCOUNT", direction=Relationship.OUTGOING)
    public Set<Account> getPublicAccounts()
    {
        return this.publicAccounts;
    }
    
    public void setupPublicAccount()
    {
        this.setupPublicAccount(null);
    }
    
    public void setupPublicAccount(String password)
    {
        Account account = new Account();
        account.setUser(this);
        if( this.getEmail() != null )
            account.setUserID(this.getEmail()); //email as userID
        else if( this.getFacebookid() != null )
            account.setUserID(this.getFacebookid());
        
        if( password == null )
        {
            String genPassword = account.generatePassword();
        }            
        else
        {
            account.setNewPassword(password);
        }
          
        this.getPublicAccounts().add(account);
    }
    
    @Relationship(type = "IS_IN", direction = Relationship.OUTGOING)
    @JsonView(EntityView.List.class)
    private Country country;

    public void setCountry(Country country){
        this.country = country;
    }
    
    public Country getCountry(){
        return this.country;
    }    

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        authorities.add(authority);
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        if( this.getPublicAccounts().size() <= 0 )
            return null;
        
        Iterator<Account> iter = this.getPublicAccounts().iterator();   
        String password = iter.next().getPassword();
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        if( this.getPublicAccounts().size() <= 0 )
            return null;
        
        Iterator<Account> iter = this.getPublicAccounts().iterator();        
        return iter.next().getUserID();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }    
}