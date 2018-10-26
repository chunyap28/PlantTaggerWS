/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.post.repository;

import com.secy.planttagger.core.BaseRepository;
import com.secy.planttagger.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends BaseRepository<Post>
{
    @Query("MATCH (m:Post)-[r:ABOUTS]->(a:Plant) WHERE a.uuid = {plant_id} RETURN m,r,a")
    Page<Post> findByPlantId(@Param("plant_id") String plantId, Pageable pageable);
}
