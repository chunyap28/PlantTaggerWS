/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.plant.repository;

import com.secy.planttagger.core.BaseRepository;
import com.secy.planttagger.plant.entity.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

public interface PlantRepository extends BaseRepository<Plant>
{
    @Query("MATCH (m:Plant)<-[r:PLANTS]-(a:User) WHERE a.uuid = {user_id} RETURN m,r,a")
    Page<Plant> findByUserId(@Param("user_id") String user_id, Pageable pageable);
}
