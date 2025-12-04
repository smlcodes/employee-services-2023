package com.employee.support.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author satyakaveti on 04/12/25
 */
@Slf4j
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic myTopic() {
        log.info("KafkaTopicConfig :: my-topic created");
        return TopicBuilder.name("my-topic")
                .partitions(3)        // Number of partitions
                .replicas(1)          // Replication factor
                .build();
    }
}
