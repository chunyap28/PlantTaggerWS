package com.secy.planttagger;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

//import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EntityScan("com.secy.planttagger")
public class Application{

    public static void main(String[] args) {
            SpringApplication.run(Application.class, args);                                
    }
    /*
    @Bean
    public FilterRegistrationBean jwtFilter() {
            final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
            registrationBean.setFilter(new JwtFilter());
            registrationBean.addUrlPatterns("/*");  //filter all

            return registrationBean;
    }*/

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
        return new TomcatEmbeddedServletContainerFactory(){
            @Override
            protected void customizeConnector(Connector connector) {
                super.customizeConnector(connector);
                connector.setParseBodyMethods("POST,PUT,DELETE");
            }
        };
    }
}