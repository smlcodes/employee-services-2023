package com.employee.service.impl;

import com.employee.api.v1.model.dto.CarbonReportDto;
import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.api.v1.model.mapper.EmployeeMapper;
import com.employee.dao.entity.Employee;
import com.employee.dao.repository.EmployeeRepository;
import com.employee.exception.BusinessException;
import com.employee.service.ReportsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.joda.time.LocalDateTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Satya Kaveti
 */


@Service
@Slf4j
@AllArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    private static final String reportPath = "download";

    private static final String templateID = "5d7077481690ec0c0c63cfc04472ccc9c2afd635ae66500eb0313571ec2ff022";
    private static final String carboneToken = "test_eyJhbGciOiJFUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI1MzE1MjUwOTE1NjI2ODY1ODUiLCJhdWQiOiJjYXJib25lIiwiZXhwIjoyMzM1MDc3OTI5LCJkYXRhIjp7ImlkQWNjb3VudCI6IjUzMTUyNTA5MTU2MjY4NjU4NSJ9fQ.AN7CtEIaJA5FUR2qkcsAs7YvABs1OMxSqC56OgdJWsz4TfYlpwm5Ow0bMz1g8DW6PX0QDLXd3onmJfg_PLQ2LU9YAV5y3bH-8fgGU-3n1jq6mWB8wm2Fp7H3ajiidfqXxZnZzw7FhqRAYlMboTArqx0S6x8-myUU5uceQfOg8PRaa4Bv";


    private final ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.mapEntityListToDtoListForEmployee(employees);
    }


    @Override
    public String employeeJasperReport(String fileType) {
        String template = "reports/emp.jrxml";
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = employeeMapper.mapEntityListToDtoListForEmployee(employees);
        return generateJasperReport(template, employeeDtos, fileType);
    }

    @Override
    public byte[] employeeCarboneReport(String fileType) {

        try {
            List<Employee> employees = employeeRepository.findAll();
            List<EmployeeDto> employeeDtos = employeeMapper.mapEntityListToDtoListForEmployee(employees);
            CarbonReportDto dto = CarbonReportDto.builder().employeeList(employeeDtos).convertTo("pdf").build();
            String renderId = getCarboneRenderId(dto);
            return downloadCarboneReport(renderId);

        } catch (JsonProcessingException e) {
            log.error("Error while converting JSON", e);
        } catch (Exception e) {
            log.error("Error while employeeCarboneReport", e);
        }
        return null;
    }


    public String generateJasperReport(String template, List dataSource, String fileType) {
        try {
            File file = ResourceUtils.getFile("classpath:" + template);
            InputStream input = new FileInputStream(file);

            // Compile the Jasper report from .jrxml to .japser
            JasperReport jasperReport = JasperCompileManager.compileReport(input);

            // Get your data source
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource);
            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Satya Kaveti");
            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);

            String fileName = "Employee_report_" + LocalDateTime.now();
            return exportJasperReport(jasperPrint, fileName, fileType);
        } catch (Exception e) {
            log.error("Error Occurred", e);
            throw new BusinessException("Error generateJasperReport");
        }
    }


    private String exportJasperReport(JasperPrint jasperPrint, String fileName, String fileType) throws Exception {
        String outputPath = "";
        switch (fileType) {
            case "csv":
                outputPath = reportPath + "//" + fileName + ".csv";
                JRCsvExporter csvExporter = new JRCsvExporter();
                csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                csvExporter.setExporterOutput(new SimpleWriterExporterOutput(outputPath));
                SimpleCsvExporterConfiguration csvConfiguration = new SimpleCsvExporterConfiguration();
                csvConfiguration.setFieldDelimiter(",");
                csvExporter.setConfiguration(csvConfiguration);
                csvExporter.exportReport();
                break;
            case "xlsx":
                outputPath = reportPath + "//" + fileName + ".xlsx";
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPath));
                SimpleXlsxReportConfiguration xlsxConfiguration = new SimpleXlsxReportConfiguration();
                xlsxConfiguration.setOnePagePerSheet(true);
                xlsxConfiguration.setRemoveEmptySpaceBetweenColumns(true);
                xlsxConfiguration.setDetectCellType(true);
                xlsxExporter.setConfiguration(xlsxConfiguration);
                xlsxExporter.exportReport();
                break;
            case "html":
                outputPath = reportPath + "//" + fileName + ".html";
                JasperExportManager.exportReportToHtmlFile(jasperPrint, outputPath);
                break;
            case "xml":
                outputPath = reportPath + "//" + fileName + ".xml";
                JasperExportManager.exportReportToXmlFile(jasperPrint, outputPath, true);
                break;
            default:
                outputPath = reportPath + "//" + fileName + ".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
        }

        log.info(fileType.toUpperCase() + " File Generated at " + outputPath);
        return outputPath;
    }


    private String getCarboneRenderId(CarbonReportDto dto) throws Exception{
        String renderId = null;
        String url = "https://api.carbone.io/render/" + templateID;
        HttpHeaders headers = new HttpHeaders();
        headers.set("carbone-version", "4");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", carboneToken);

        // Convert dto to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String requestBody = objectMapper.writeValueAsString(dto);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        log.info("Response: " + response.getBody());

        if(!ObjectUtils.isEmpty(response)){
            String responseBody = response.getBody();
            JSONObject jsonResponse = new JSONObject(responseBody);
            renderId = jsonResponse.getJSONObject("data").getString("renderId");
            log.info("Render Id "+renderId);
        }
        return renderId;
    }


    public byte[]  downloadCarboneReport(String renderId) throws IOException {
        String url = "https://api.carbone.io/render/"+renderId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("carbone-version", "4");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
        return response.getBody();
    }

}


