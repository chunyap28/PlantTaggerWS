/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.account.repository;

import com.secy.planttagger.account.entity.RefreshToken;
import com.secy.planttagger.core.BaseRepository;

/**
 *
 * @author chunyap
 */
public interface RefreshTokenRepository extends BaseRepository<RefreshToken>{
    public RefreshToken findByToken(String token);
}