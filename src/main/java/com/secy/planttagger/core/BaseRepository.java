/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.core;

import java.util.*;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chunyap
 */
@Repository
public interface BaseRepository<T extends BaseEntity> extends GraphRepository<T>{
    
    public T findByUuid(String uuid);
}
