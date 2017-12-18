/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.friend.repository;

import com.secy.planttagger.friend.entity.Friend;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chunyap
 */
@Repository
public interface FriendRepository extends GraphRepository<Friend>{    
}