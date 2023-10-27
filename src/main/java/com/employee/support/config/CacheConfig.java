package com.employee.support.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Application Cache configuration. SpringBoot will auto-bootstrap the CacheManager depending on the property spring.cache.type 
 * and the existence of a supported Cache Provider on the Classpath.
 * 
 * @author Satya
 */
@Configuration
@EnableCaching
public class CacheConfig {

}