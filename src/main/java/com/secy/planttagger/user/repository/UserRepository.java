/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.user.repository;

//import org.springframework.data.mongodb.repository.MongoRepository;
import com.secy.planttagger.core.BaseRepository;
import com.secy.planttagger.user.entity.User;

public interface UserRepository extends BaseRepository<User>
{
//MongoRepository<User, String> {
    
    public User findByName(String Name);
    public User findByFacebookid(String Facebook_id);
    public User findByEmailOrFacebookid(String Email, String Facebook_id);
    //public List<User> findByLastName(String lastName);

}
