package com.employee.api.v1.kafka;

import com.employee.api.v1.model.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * @author satyakaveti on 05/12/25
 */
// HR System publishes onboarding events
@RestController
@RequestMapping("/api/hr")
@Slf4j
public class HRSystemController {

    private final KafkaTemplate<String, HREmployeeOnboardingEvent> kafkaTemplate;

    public HRSystemController(KafkaTemplate<String, HREmployeeOnboardingEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/onboard")
    public ResponseEntity<Map<String, Object>> onboardEmployee(
            @RequestBody EmployeeDto request) {

        String eventId = UUID.randomUUID().toString();

        HREmployeeOnboardingEvent event = HREmployeeOnboardingEvent.builder()
                .eventId(eventId)
                .timestamp(LocalDateTime.now())
                .employeeData(request)
                .build();

        // Publish to Kafka
        kafkaTemplate.send("employee.onboarding", event.getEmployeeData().getId().toString(), event)
                .addCallback(
                        result -> log.info("✅ Onboarding event published: {}", eventId),
                        ex -> log.error("❌ Failed to publish onboarding event: {}",
                                ex.getMessage())
                );

        return ResponseEntity.accepted()
                .body(Map.of(
                        "status", "ACCEPTED",
                        "message", "Employee onboarding initiated",
                        "eventId", eventId,
                        "employeeId", event.getEmployeeData().getId(),
                        "estimatedCompletion", "Within 5 minutes"
                ));
    }

}
