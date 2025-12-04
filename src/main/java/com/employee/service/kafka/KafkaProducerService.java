package com.employee.service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author satyakaveti on 04/12/25
 */
@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "my-topic";

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSimpleMessage(String topic, String message) {
        // Simplest form: send to topic with a value
        kafkaTemplate.send(topic, message);
    }

    public void sendMessageWithKey(String topic, String key, String value) {
        // Send with a specific key (messages with same key go to same partition)
        kafkaTemplate.send(topic, key, value);
    }

    public void sendMessageWithCallback(String topic, String message) {
        // Handle success or failure asynchronously
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message).completable();
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent successfully to partition " +
                        result.getRecordMetadata().partition());
            } else {
                System.err.println("Failed to send: " + ex.getMessage());
            }
        });
    }
}