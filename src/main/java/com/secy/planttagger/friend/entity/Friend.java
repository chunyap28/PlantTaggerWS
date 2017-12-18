/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.friend.entity;

import java.util.Date;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import com.secy.planttagger.user.entity.User;

/**
 *
 * @author chunyap
 */
@RelationshipEntity(type = "FRIEND")
public class Friend {
    @GraphId private Long id;
    @StartNode User user;
    @EndNode User friend;
    Date since;
    
    public Friend(){
    }
    
    public Friend(User user, User friend, Date since) {
        this.user = user;
        this.friend = friend;
        this.since = since;            

        this.user.getFriends().add(this);
        this.friend.getFriends().add(this);
    }
}