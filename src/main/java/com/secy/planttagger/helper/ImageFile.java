/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.helper;

import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author chunyap
 */
public class ImageFile {
    
    protected MultipartFile img;

    /**
     * @return the img
     */
    public MultipartFile getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(MultipartFile img) {
        this.img = img;
    }
    
    
}
