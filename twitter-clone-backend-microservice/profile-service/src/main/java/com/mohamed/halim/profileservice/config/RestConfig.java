package com.mohamed.halim.profileservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {


    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate =  new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        return restTemplate;

    }
    
}