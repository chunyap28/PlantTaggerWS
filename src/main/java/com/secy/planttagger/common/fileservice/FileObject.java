/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.common.fileservice;

import com.secy.planttagger.core.Mapper;

public class FileObject extends Mapper<FileObject>{
    
    protected FileReference reference;
    protected byte[] content;

    public FileObject(FileReference reference, byte[] content)
    {
        this.reference = reference;
        this.content = content;
    }
    /**
     * @return the reference
     */
    public FileReference getReference() {
        return reference;
    }

    /**
     * @param reference the file to set
     */
    public void setFile(FileReference reference) {
        this.reference = reference;
    }

    /**
     * @return the content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(byte[] content) {
        this.content = content;
    }
}