/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.country.repository;

import com.secy.planttagger.core.BaseRepository;
import com.secy.planttagger.country.entity.Country;

public interface CountryRepository extends BaseRepository<Country>
{
    public Country findByCode(String Code);
}
