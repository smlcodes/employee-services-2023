package com.employee.api.v1;

import com.employee.ApplicationConstants;
import com.employee.api.v1.model.mapper.EmployeeMapper;
import com.employee.exception.BusinessException;
import com.employee.service.ReportsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Satya Kaveti
 */

@RestController
@RequestMapping(path = ApplicationConstants.API_BASE + ApplicationConstants.V1 + "reports")
@Validated
@Slf4j
@RequiredArgsConstructor
public class ReportsController {

    @Autowired
    private final ReportsService reportsService;

    @GetMapping("/jasper/employee")
    public ResponseEntity<Resource> employeeJasperReport(@RequestParam("fileType") String fileType) {
        try {
            String downloadFilePath = reportsService.employeeJasperReport(fileType);
            Resource resource = new FileSystemResource(downloadFilePath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", downloadFilePath);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("Downloading Employees JSON Failed, Due to : " + e.getMessage());
        }
    }
}