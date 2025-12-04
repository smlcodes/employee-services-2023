package com.employee.api.v1;

import com.employee.ApplicationConstants;
import com.employee.api.v1.model.KafkaMessageRequest;
import com.employee.service.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author satyakaveti on 04/12/25
 */

@RestController
@RequestMapping(path = ApplicationConstants.API_BASE + ApplicationConstants.V1 + "kafka")
@Validated
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageController {

    @Autowired
    KafkaProducerService kafkaProducerService;

    // Endpoint 1: Send simple message
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody KafkaMessageRequest request) {
        try {
            kafkaProducerService.sendSimpleMessage(request.getTopic(), request.getMessage());
            return ResponseEntity.ok("Message sent successfully to topic: " + request.getTopic());
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Failed to send message: " + e.getMessage());
        }
    }

    // Endpoint 2: Send message with key
    @PostMapping("/send-with-key")
    public ResponseEntity<String> sendMessageWithKey(@RequestBody KafkaMessageRequest request) {
        try {
            kafkaProducerService.sendMessageWithKey(request.getTopic(), request.getKey(), request.getMessage());
            return ResponseEntity.ok(String.format("Message sent with key '%s' to topic", request.getKey()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send message with key: " + e.getMessage());
        }
    }

    // Endpoint 3: Send message with callback (asynchronous)
    @PostMapping("/send-async")
    public ResponseEntity<String> sendMessageAsync(@RequestBody KafkaMessageRequest request) {
        try {
            kafkaProducerService.sendMessageWithCallback(request.getTopic(), request.getMessage());
            return ResponseEntity.accepted().body("Message sending initiated asynchronously");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to initiate async send: " + e.getMessage());
        }
    }

}
