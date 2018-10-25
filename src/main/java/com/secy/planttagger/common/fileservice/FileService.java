/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.common.fileservice;

import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;

@Service("FileService")
@Component
public class FileService {
    
    @Value("${planttagger.uploadpath}")
    private String basepath;
    
    public void upload(FileReference reference, byte[] content) throws IOException
    {
        System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
        
        Path path = Paths.get(basepath + reference.getUuid());
        Files.write(path, content);       
    }
    
    public FileObject retrieve(FileReference reference) throws IOException
    {
        System.out.println("Getting File = " + reference.getUuid());
        Path path = Paths.get(basepath + reference.getUuid());
        
        return new FileObject(reference, Files.readAllBytes(path));
    }
}