package com.employee;

/**
 * @author satyakaveti on 05/12/25
 */

import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.service.kafka.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@EnableScheduling
public class TaskSchedular {
    static final String TOPIC = "my-topic";

    @Autowired
    KafkaProducerService kafkaProducerService;

    //Sends a test message to Kafka every 5 second
    //@Scheduled(cron = "*/30 * * * * *") // Every 5 minutes at second 0
    public void sendKafkaMessage() throws InterruptedException {
        for (Long i = 0L; i < 10; i++) {
            EmployeeDto dto = EmployeeDto.builder().id(i).name("Name_" + i).build();
            kafkaProducerService.sendSimpleMessage(TOPIC, dto.toString());
            TimeUnit.SECONDS.sleep(5);
            log.info("sendKafkaMessage : " + dto);
        }
    }
}
