package com.employee.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * @author satyakaveti on 05/12/25
 */

@Service
@Slf4j
public class KafkaConsumerService {
    // Basic listener
    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void listen(String message) {
        log.info("Received: " + message);
    }

    /* Listener with metadata access - This will work only if you send Key & Partition in the message, otherwise it will Throw Errors at startup.
    So, Please enable this method only if you are sending Headers */
   /* @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void listenWithMetadata(
            @Payload String value,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("Key: %s, Value: %s, Partition: %d%n", key, value, partition);
    }*/
}

