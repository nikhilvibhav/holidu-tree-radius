package com.holidu.interview.assignment.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Boot configuration class
 *
 * @author Nikhil Vibhav
 */
@Configuration
public class AppConfig {

    /**
     * Builds a {@link RestTemplate} bean
     *
     * @return an instance of {@link RestTemplate}
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplateBuilder().build();
    }
}
