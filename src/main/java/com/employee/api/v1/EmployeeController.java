package com.employee.api.v1;

import com.employee.ApplicationConstants;
import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.api.v1.model.dto.EmployeeSearchDto;
import com.employee.api.v1.model.dto.EmployeeSearchResultsDto;
import com.employee.api.v1.model.dto.RequestDto;
import com.employee.api.v1.model.mapper.EmployeeMapper;
import com.employee.exception.BusinessException;
import com.employee.service.EmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Satya Kaveti
 */

@RestController
@RequestMapping(path = ApplicationConstants.API_BASE + ApplicationConstants.V1 + "employee")
@Validated
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    private final ObjectMapper objectMapper;

    private final EmployeeMapper employeeMapper;


    @ApiOperation("Create a new Employee or update exist ")
    @PostMapping
    public EmployeeDto save(@RequestParam(name = "id", required = false) Long id, @RequestBody @Valid EmployeeDto employeeDto) {
        return employeeService.save(employeeDto, id);
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.getEmployeeById(id);
    }

    @ApiOperation("Get Employee Version History By Id")
    @GetMapping("/all")
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }


    @ApiOperation("Returns a page of all Employee list")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 403, message = "Unauthorized")})
    @PostMapping("/search")
    public Page<EmployeeSearchResultsDto> searchAllEmployee(Pageable pageRequest, @RequestBody EmployeeSearchDto searchCriteria) {
        return employeeService.searchAllEmployee(pageRequest, searchCriteria);
    }


    @ApiOperation("Deletes one or more Employee from the system by their Id's. Always returns 200.")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        this.employeeService.delete(id);
    }

    @ApiOperation("Get Employee Version History By Id")
    @GetMapping("/{id}/history")
    public List<EmployeeDto> getEmployeeHistoryById(@PathVariable("id") Long id, Pageable pageable) {
        return employeeService.getEmployeeHistoryById(id, pageable);
    }

    @ApiOperation("Email All Employees Data on Scheduled Time")
    @PostMapping("/schedule")
    public void scheduleTask(@RequestBody RequestDto requestDto) {
        employeeService.scheduleTask(requestDto.getCron());
    }


    @GetMapping("/json/export")
    public ResponseEntity<Resource> exportEmployeesToJson() {
        try {
            List<EmployeeDto> employees = employeeService.getAllEmployees();
            String jsonData = objectMapper.writeValueAsString(employees);
            ByteArrayResource resource = new ByteArrayResource(jsonData.getBytes());
            String fileName = "employees_" + LocalDateTime.now() + ".json";
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").contentLength(resource.contentLength()).contentType(MediaType.APPLICATION_JSON).body(resource);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("Downloading Employees JSON Failed, Due to : " + e.getMessage());
        }
    }

    @PostMapping("/json/import")
    public EmployeeDto importEmployees(MultipartFile file) {
        try {
            String jsonContent = new String(file.getBytes());
            EmployeeDto employeeDto = objectMapper.readValue(jsonContent, new TypeReference<>() {
            });
            return employeeService.save(employeeDto, employeeDto.getId());
        } catch (Exception e) {
            log.error("error : {}", e.getMessage());
            throw new BusinessException("Importing Employee JSON Failed, Due to : " + e.getMessage());
        }
    }

}