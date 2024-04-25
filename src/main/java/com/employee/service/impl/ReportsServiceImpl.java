package com.employee.service.impl;

import com.employee.api.v1.model.dto.CarbonReportDto;
import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.api.v1.model.dto.UserDto;
import com.employee.api.v1.model.mapper.EmployeeMapper;
import com.employee.api.v1.model.mapper.UserMapper;
import com.employee.constants.ReportTypeEnum;
import com.employee.dao.entity.Employee;
import com.employee.dao.entity.User;
import com.employee.dao.repository.EmployeeRepository;
import com.employee.dao.repository.UserRepository;
import com.employee.service.ReportsService;
import com.employee.utils.JasperReportsUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
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

    private static final String templateID = "5d7077481690ec0c0c63cfc04472ccc9c2afd635ae66500eb0313571ec2ff022";
    private static final String carboneToken = "test_eyJhbGciOiJFUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI1MzE1MjUwOTE1NjI2ODY1ODUiLCJhdWQiOiJjYXJib25lIiwiZXhwIjoyMzM1MDc3OTI5LCJkYXRhIjp7ImlkQWNjb3VudCI6IjUzMTUyNTA5MTU2MjY4NjU4NSJ9fQ.AN7CtEIaJA5FUR2qkcsAs7YvABs1OMxSqC56OgdJWsz4TfYlpwm5Ow0bMz1g8DW6PX0QDLXd3onmJfg_PLQ2LU9YAV5y3bH-8fgGU-3n1jq6mWB8wm2Fp7H3ajiidfqXxZnZzw7FhqRAYlMboTArqx0S6x8-myUU5uceQfOg8PRaa4Bv";


    private final ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;


    @Autowired
    private JasperReportsUtil jasperReportsUtil;

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.mapEntityListToDtoListForEmployee(employees);
    }

    public List<UserDto> getAllUsers() {
        //List<User> users = userRepository.findAll();

        List<User> users = userRepository.findFirst10ByOrderById();
        return userMapper.toUserDtoList(users);
    }



    @Override
    public String employeeJasperReport(String fileType) {
        String template = "reports/emp.jrxml";
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = employeeMapper.mapEntityListToDtoListForEmployee(employees);
        return jasperReportsUtil.generateJasperReportToFile(template, employeeDtos, fileType);
    }

    @Override
    public byte[] employeeJasperReport24(String fileType) {
        String template = "reports/emp24.jrxml";
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = employeeMapper.mapEntityListToDtoListForEmployee(employees);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("comanyName", "BLACK STAR TECHNOLOGIES");
        parameters.put("address", "Address: Raheja Mind Space Entrance Gate, HITEC City, Hyderabad -500081");
        parameters.put("header", "Employees Salary Report");

        try {
            FileInputStream leafBannerStream = new FileInputStream(ResourceUtils.getFile("classpath:reports/logo.jpg").getAbsolutePath());
            parameters.put("logo", leafBannerStream);
        } catch (Exception e) {
            log.error("Logo error: ", e);
        }
        return jasperReportsUtil.generateJasperReportBytes(template, parameters, employeeDtos, fileType);
    }



    @Override
    public byte[] userJasperReport(String fileType) {
        String template = "reports/users.jrxml";
        List<UserDto> userDtos = getAllUsers();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("header", "User Accounts - Sub Report");
        return jasperReportsUtil.generateJasperReportBytes(template, parameters, userDtos, fileType);
    }

    @Override
    public byte[] jasperSubreport(String fileType) throws Exception {

        //1. Create Required Parameters
        Map<String, Object> parameters = new HashMap<>();
        FileInputStream leafBannerStream = new FileInputStream(ResourceUtils.getFile("classpath:reports/logo.jpg").getAbsolutePath());
        parameters.put("comanyName", "BLACK STAR TECHNOLOGIES");
        parameters.put("address", "Address: Raheja Mind Space Entrance Gate, HITEC City, Hyderabad -500081");
        parameters.put("header", "Employees Salary Report");
        parameters.put("logo", leafBannerStream);
        parameters.put("createdBy","Satya Kaveti");


        //2. Create SubReport Data & assign it to Parameters
        List<UserDto>  userDtos = getAllUsers();
        JasperReport subReport = JasperCompileManager.compileReport(ResourceUtils.getFile("classpath:reports/users.jrxml").getAbsolutePath());
        JRBeanCollectionDataSource subDatasource = new JRBeanCollectionDataSource(userDtos);
        Map<String, Object> subParameters = new HashMap<>();
        subParameters.put("header", "User Accounts - Sub Report");
        //add to main report parameters
        parameters.put("subReport",subReport);
        parameters.put("subDatasource",subDatasource);
        parameters.put("subParameters",subParameters);


        //3.Create Main Report Data
        List<EmployeeDto> employeeDtos = getAllEmployees();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeDtos);

        String template = "reports/empSubReport.jrxml";
        String mainPath = ResourceUtils.getFile("classpath:" + template).getAbsolutePath();
        JasperReport mainReport = JasperCompileManager.compileReport(mainPath);

        //4.Fill Report - by passing complied .jrxml object, paramters, datasource
        JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, dataSource);

        //5.Export Report - by using JasperExportManager
        ReportTypeEnum reportType = ReportTypeEnum.getReportTypeByCode(fileType);
        return jasperReportsUtil.exportJasperReportBytes(jasperPrint, reportType);
    }


    public byte[] employeeJasperReportInBytes(String fileType) throws Exception {
        String template = "reports/emp24.jrxml";
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> dataSource = employeeMapper.mapEntityListToDtoListForEmployee(employees);


        //1. Create Required Parameters
        Map<String, Object> parameters = new HashMap<>();
        FileInputStream leafBannerStream = new FileInputStream(ResourceUtils.getFile("classpath:reports/logo.jpg").getAbsolutePath());
        parameters.put("comanyName", "BLACK STAR TECHNOLOGIES");
        parameters.put("address", "Address: Raheja Mind Space Entrance Gate, HITEC City, Hyderabad -500081");
        parameters.put("header", "Employees Salary Report");
        parameters.put("logo", leafBannerStream);
        parameters.put("createdBy","Satya Kaveti");

        //2.Create DataSource
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataSource);

        //3.Compile .jrmxl template, stored in JasperReport object
        String path = ResourceUtils.getFile("classpath:" + template).getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);

        //4.Fill Report - by passing complied .jrxml object, paramters, datasource
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);

        //5.Export Report - by using JasperExportManager
        ReportTypeEnum reportType = ReportTypeEnum.getReportTypeByCode(fileType);
        return jasperReportsUtil.exportJasperReportBytes(jasperPrint, reportType);
    }






    //******************************************************
    //              Carbone Reports
    //******************************************************


    //1. Render Report with Data
    private String getCarboneRenderId(CarbonReportDto dto) throws Exception {
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

        if (!ObjectUtils.isEmpty(response)) {
            String responseBody = response.getBody();
            JSONObject jsonResponse = new JSONObject(responseBody);
            renderId = jsonResponse.getJSONObject("data").getString("renderId");
            log.info("Render Id " + renderId);
        }
        return renderId;
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


    public byte[] downloadCarboneReport(String renderId) throws IOException {
        String url = "https://api.carbone.io/render/" + renderId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("carbone-version", "4");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
        return response.getBody();
    }


}


