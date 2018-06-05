/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.plant.repository;

import com.secy.planttagger.core.BaseRepository;
import com.secy.planttagger.plant.entity.PlantImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

public interface PlantImageRepository extends BaseRepository<PlantImage>
{
    @Query("MATCH (m:PlantImage)-[r:BELONGS_TO]->(a:Plant) WHERE a.uuid = {plant_id} RETURN m,r,a")
    Page<PlantImage> findByPlantId(@Param("plant_id") String plant_id, Pageable pageable);
}