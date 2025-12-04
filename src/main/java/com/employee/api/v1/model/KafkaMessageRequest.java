package com.employee.api.v1.model;

import lombok.Data;

/**
 * @author satyakaveti on 04/12/25
 */
@Data
public class KafkaMessageRequest {
    private String key;
    private String message;
    private String topic;
}