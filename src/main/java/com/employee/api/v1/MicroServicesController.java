package com.employee.api.v1;

import com.employee.ApplicationConstants;
import com.employee.api.v1.model.dto.EmailRequestDto;
import com.employee.service.MicroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author satyakaveti on 10/04/24
 */

@RestController
@RequestMapping(path = ApplicationConstants.API_BASE + ApplicationConstants.V1 + "microservice")
public class MicroServicesController {

    @Autowired
    MicroService microService;

    @PostMapping("/email")
    private void sendEmployeeDataMail(@RequestBody EmailRequestDto dto){
        microService.sendEmployeeDataMail(dto);
    }

    @PostMapping("/email/reports")
    private void sendEmployeeDataMailWithAttachments(@RequestBody EmailRequestDto dto){
        microService.sendEmployeeDataMailWithAttachments(dto);
    }



}
