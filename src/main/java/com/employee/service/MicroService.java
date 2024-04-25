package com.employee.service;

import com.employee.api.v1.model.dto.EmailRequestDto;

/**
 * @author satyakaveti on 10/04/24
 */
public interface MicroService {


    public void sendEmployeeDataMail(EmailRequestDto dto);

    public void sendEmployeeDataMailWithAttachments(EmailRequestDto dto);

}
