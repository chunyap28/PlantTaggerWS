/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.common.emailservice;

import java.util.List;
import com.secy.planttagger.core.Mapper;
import java.util.ArrayList;

public class EmailObject extends Mapper<EmailObject>{
    
    protected List<String> to;
    protected String content;
    protected String subject;

    public EmailObject() {       
        to = new ArrayList<>();
    }
    /**
     * @return the to
     */
    public List<String> getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(List<String> to) {
        this.to = to;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }   
}
