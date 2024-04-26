package com.employee.support.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author satyakaveti on 26/04/24
 */

@Configuration
@Slf4j
public class CommonConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
