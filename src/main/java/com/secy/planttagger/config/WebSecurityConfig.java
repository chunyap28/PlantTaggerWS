/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.config;

import com.secy.planttagger.auth.*;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;
        
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    /*
    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
       org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
       config
           .driverConfiguration()
           .setURI("http://localhost:7474");
       return config;
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }  
    
    @Bean
    public FacebookTokenAuthenticationFilter facebookTokenAuthenticationFilterBean() throws Exception {
        return new FacebookTokenAuthenticationFilter(authenticationManager());
    }
    
    @Bean
    public PasswordAuthenticationFilter passwordAuthenticationFilterBean() throws Exception {
        return new PasswordAuthenticationFilter(authenticationManager());
    }
    
    @Bean
    public RefreshTokenAuthenticationFilter refreshTokenAuthenticationFilter() throws Exception {
        return new RefreshTokenAuthenticationFilter(authenticationManager());
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
      
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .anyRequest().authenticated();
        

          // Custom JWT based security filter
          httpSecurity
                  .addFilterBefore(facebookTokenAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class) //facebook login
                  .addFilterBefore(passwordAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class) //password login
                  .addFilterBefore(refreshTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) //refresh token login
                  .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

          // disable page caching
          httpSecurity.headers().cacheControl();
    }
}
