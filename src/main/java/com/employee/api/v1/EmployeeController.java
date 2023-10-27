
package com.employee.api.v1;

import com.employee.ApplicationConstants;
import com.employee.api.v1.model.dto.*;
import com.employee.service.EmployeeService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("Returns a page of all Employee list")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 403, message = "Unauthorized")})
    @PostMapping("/search")
    public Page<EmployeeSearchResultsDto> searchAllEmployee(Pageable pageRequest, @RequestBody EmployeeSearchDto searchCriteria) {
        return employeeService.searchAllEmployee(pageRequest, searchCriteria);
    }

    @ApiOperation("Create a new Employee or update exist ")
    @PostMapping
    public EmployeeDto save(@RequestParam(name = "id", required = false) Long id, @RequestBody EmployeeDto employeeDto) {
        return employeeService.save(employeeDto, id); 
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.getEmployeeById(id);
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
}