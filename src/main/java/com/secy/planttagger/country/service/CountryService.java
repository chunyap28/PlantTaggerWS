/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.country.service;

import com.secy.planttagger.country.entity.Country;
import com.secy.planttagger.country.repository.CountryRepository;
import com.secy.planttagger.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author chunyaplim
 */
@Service("CountryService")
public class CountryService {
    @Autowired private CountryRepository countryRepository;
    
    public Country getByCode(String code){
        Country country = countryRepository.findByCode(code);
        if( country == null ){
            throw new ObjectNotFoundException("Country");
        }
        
        return country;
    }
    
    public Page<Country> getAll(Pageable page)
    {
        return countryRepository.findAll(page);
    }
}
