package com.employee.api.v1.kafka;

import com.employee.api.v1.model.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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

    @Autowired
    @Qualifier("jsonKafkaTemplate")
    KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/onboard")
    public ResponseEntity<?> onboardEmployee(@RequestBody EmployeeDto request) {

        log.info("Preparing onboarding event by HR System");
        String eventId = UUID.randomUUID().toString();
        HREmployeeOnboardingEvent event = HREmployeeOnboardingEvent.builder()
                .eventId(eventId)
                .timestamp(LocalDateTime.now())
                .employeeData(request)
                .build();

        log.info("Sending HREmployeeOnboardingEvent: {}", event);
        try {
            // Send asynchronously and wait for response
            kafkaTemplate.send("employee.onboarding", event.getEmployeeData().getId().toString(), event).get();   // <--- ensures exception will be thrown if Kafka send fails
            log.info("✅ Successfully published onboarding event: {}", eventId);
            return ResponseEntity.ok(event);   // return full event JSON (200)
        } catch (Exception ex) {
            log.error("❌ Failed to publish onboarding event: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "ERROR",
                            "message", "Failed to publish onboarding event",
                            "error", ex.getMessage()
                    ));
        }
    }

}
