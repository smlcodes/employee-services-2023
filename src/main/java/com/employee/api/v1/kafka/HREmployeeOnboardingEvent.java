package com.employee.api.v1.kafka;

import com.employee.api.v1.model.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author satyakaveti on 05/12/25
 */
@Data
@Builder
public class HREmployeeOnboardingEvent {
    private String eventId;
    private String eventType = "HR_EMPLOYEE_ONBOARDING_INITIATED";
    private LocalDateTime timestamp;
    private EmployeeDto employeeData;
}
