package com.employee.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author satyakaveti on 14/05/24
 */

@Component
@Data
public class ApplicationUtils {

    @Value("${app.isEmailEnabledForUserCreation: false}")
    private Boolean isEmailEnabledForUserCreation;

    @Value("${app.isFeignEnabled: false}")
    private Boolean isFeignEnabled;

}
