package com.employee.api.v1.kafka;

import com.employee.api.v1.model.dto.EmployeeDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author satyakaveti on 05/12/25
 */
@Data
@Builder
public class HREmployeeOnboardingEvent {
    private String eventId;
    private String eventType = "HR_EMPLOYEE_ONBOARDING_INITIATED";
    private Date timestamp;
    private EmployeeDto employeeData;
}
