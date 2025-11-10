package org.example.homework7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Configuration
public class Config {
    @Bean(name = "restTemplate")
    public RestTemplate getRestTemplateShort() {
        System.out.println("getRestTemplate");
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3 * 1000);
        requestFactory.setReadTimeout(3 * 1000);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    @Bean
    @Scope("prototype")
    public ArrayList<?> getArrayList() {
        return new ArrayList<>();
    }
}
