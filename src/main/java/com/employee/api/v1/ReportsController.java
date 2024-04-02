package com.employee.api.v1;

import com.employee.ApplicationConstants;
import com.employee.exception.BusinessException;
import com.employee.service.ReportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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


    @GetMapping("/carbone/employee")
    public ResponseEntity<Resource> employeeCarboneReport(@RequestParam("fileType") String fileType) {
        byte[] bytes = reportsService.employeeCarboneReport(fileType);
        if (null != bytes) {
            ByteArrayResource resource = new ByteArrayResource(bytes);
            String fileName = "Employee_Carbone_Report" + "_" + LocalDate.now() + ".pdf";
            return ResponseEntity.ok()
                    .header(com.google.common.net.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            throw new BusinessException("File Download Failed");
        }
    }
}