package com.employee.api.v1;

import com.employee.ApplicationConstants;
import com.employee.api.v1.model.dto.EmailRequestDto;
import com.employee.service.MicroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author satyakaveti on 10/04/24
 */

@RestController
@RequestMapping(path = ApplicationConstants.API_BASE + ApplicationConstants.V1 + "microservice")
public class MicroServicesController {

    @Autowired
    MicroService microService;

    @PostMapping("/email")
    private ResponseEntity<String> sendEmail(@RequestBody EmailRequestDto dto) {
        return microService.sendEmployeeDataMail(dto);
    }

    @GetMapping("/email")
    private ResponseEntity<String> getEmail() {
        return microService.getEmail();
    }

}
