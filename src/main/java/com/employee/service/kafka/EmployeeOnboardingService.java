package com.employee.service.kafka;

import com.employee.api.v1.kafka.HREmployeeOnboardingEvent;
import com.employee.api.v1.model.dto.EmailRequestDto;
import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.service.EmployeeService;
import com.employee.support.feign.EmailFeignClient;
import com.employee.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author satyakaveti on 05/12/25
 */
// Main Employee Service - Processes onboarding events
@Service
@Slf4j
@Transactional
public class EmployeeOnboardingService {


    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmailFeignClient emailFeignClient;

    @KafkaListener(
            topics = "employee.onboarding",
            groupId = "employee-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void processOnboardingEvent(HREmployeeOnboardingEvent event) {
        log.info("📥 Received onboarding event for employee: {}", event.getEmployeeData().getId());
        try {
            // Step 1: Create employee in database
            EmployeeDto employee = employeeService.save(event.getEmployeeData());

            // Step 2: Send welcome email (asynchronous)
            CompletableFuture.runAsync(() -> sendWelcomeEmail(event));

            // Step 3: Publish success events
            publishSuccessEvents(event);

            log.info("✅ Onboarding completed for employee: {}", employee.getId());

        } catch (Exception e) {
            log.error("❌ Onboarding failed for employee {}: {}", event.getEmployeeData().getId(), e.getMessage(), e);
            publishFailureEvent(event, "PROCESSING_FAILED", e.getMessage());
            throw e; // Let Kafka retry or DLQ handle
        }
    }


    private void sendWelcomeEmail(HREmployeeOnboardingEvent event) {
        EmployeeDto employeeDto = event.getEmployeeData();
        try {
            log.info("Send Mail when ever new user created, by email service");
            employeeDto.getAccount().setPassword(RandomStringUtils.randomAlphanumeric(12));
            EmailRequestDto emailRequestDto = EmailUtil.getEmailDtoFromEmployee(employeeDto);
            emailFeignClient.sendEmail(emailRequestDto);


        } catch (Exception e) {
            log.error("Failed to send welcome email to {}: {}", employeeDto.getAccount().getEmail(), e.getMessage());
            // Publish email failure event for retry
            publishFailureEvent(event, "Welcome_Email_FAILED", e.getMessage());
        }
    }

    private void publishSuccessEvents(HREmployeeOnboardingEvent originalEvent) {
        // Build a failure event map
        Map<String, Object> successEvent = new HashMap<>();
        successEvent.put("eventId", UUID.randomUUID().toString());
        successEvent.put("eventType", "EMPLOYEE_ONBOARDING_COMPLETED");
        successEvent.put("timestamp", LocalDateTime.now().toString());
        successEvent.put("originalEventId", originalEvent.getEventId());
        successEvent.put("employeeData", originalEvent.getEmployeeData().getId());
        successEvent.put("originalEvent", originalEvent);
        kafkaTemplate.send("employee.onboarding.completed", originalEvent.getEmployeeData().getId().toString(), successEvent);
    }


    private void publishFailureEvent(HREmployeeOnboardingEvent originalEvent, String failureType, String errorMessage) {
        // Build a failure event map
        Map<String, Object> failureEvent = new HashMap<>();
        failureEvent.put("eventId", UUID.randomUUID().toString());
        failureEvent.put("eventType", "EMPLOYEE_ONBOARDING_FAILED");
        failureEvent.put("timestamp", LocalDateTime.now().toString());
        failureEvent.put("originalEventId", originalEvent.getEventId());
        failureEvent.put("employeeId", originalEvent.getEmployeeData().getId());
        failureEvent.put("failureType", failureType); // e.g., "VALIDATION_FAILED", "PROCESSING_FAILED"
        failureEvent.put("errorMessage", errorMessage);
        failureEvent.put("originalEvent", originalEvent);

        try {
            // Send to a dedicated Dead-Letter Topic (DLT) for failed onboarding events
            kafkaTemplate.send("employee.onboarding.failed",
                            originalEvent.getEmployeeData().getId().toString(),
                            failureEvent)
                    .addCallback(
                            result -> log.warn("Published failure event for employee {}: {}",
                                    originalEvent.getEmployeeData().getId(),
                                    failureType),
                            ex -> log.error("CRITICAL: Could not publish failure event for {}: {}",
                                    originalEvent.getEmployeeData().getId(),
                                    ex.getMessage())
                    );
        } catch (Exception e) {
            log.error("Fatal: Failed to publish failure event to Kafka", e);
        }
    }

}
