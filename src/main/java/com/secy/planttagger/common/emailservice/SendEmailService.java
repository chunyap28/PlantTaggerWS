/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.common.emailservice;

import com.secy.planttagger.common.emailservice.EmailObject;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@Service("SendEmailService")
@Component
public class SendEmailService {
    
    @Autowired private JavaMailSender sender;
    @Autowired private RabbitTemplate rabbitTemplate;
    
    public void sendAsync(EmailObject email)
    {       
        try{
            System.out.println("Sending message..." + email.toJson());
            //send to queue with default exchange
            this.rabbitTemplate.convertAndSend(SendEmailConfig.queueName, email.toJson());            
        }
        catch(AmqpException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void onNewMessage(String json)
    {
        try {
            EmailObject email = EmailObject.fromJson(json, EmailObject.class);
            System.out.println("Receiving message..." + email.toJson());
            sendEmail(email);
        } catch (JsonMappingException ex) {
            Logger.getLogger(SendEmailService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SendEmailService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SendEmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendEmail(EmailObject email) throws Exception{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
                
        helper.setTo(StringUtils.join(email.getTo(), ','));
        helper.setText(email.getContent());
        helper.setSubject(email.getSubject());
        
        sender.send(message);
    }
}
