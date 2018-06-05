/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.core;

import org.springframework.security.access.prepost.PostAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;

public interface IPlantTaggerService<T extends BaseEntity> {

    @PostAuthorize ("returnObject.owner == authentication.name")
    public T findByUuid(String uuid);
}
