
package com.employee.service.impl;

import com.employee.api.v1.model.dto.*;
import com.employee.api.v1.model.mapper.EmployeeMapper;
import com.employee.dao.entity.Employee;
import com.employee.dao.repository.EmployeeRepository;
import com.employee.exception.BusinessException;
import com.employee.exception.EntityNotFoundException;
import com.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Satya Kaveti
 */


@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public Page<EmployeeSearchResultsDto> searchAllEmployee(Pageable pageRequest, EmployeeSearchDto searchCriteria) {
        return employeeRepository.employeeSearchCriteria(pageRequest, searchCriteria);
    }

    @Transactional()
    @Override
    public EmployeeDto save(EmployeeDto employeeDto, Long employeeId) {
        log.info("save start :::: employeeId:{}", employeeId);
        try {
            Boolean createRequest = StringUtils.isEmpty(employeeId) ? Boolean.TRUE : Boolean.FALSE;
            Employee employee = getEmployee(employeeId, createRequest);
            employeeMapper.toEntity(employeeDto, employee);
            Employee entity = employeeRepository.save(employee);
            return employeeMapper.toDto(entity);
        } catch (Exception ex) {
            log.error("Error while saving employee", ex);
            throw new BusinessException("Save Failed");
        }
    }


    @Override
    public EmployeeDto getEmployeeById(Long id) {
        var entity = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Employee.class, id));
       return employeeMapper.toDto(entity);
    }


    private Employee getEmployee(Long employeeId, Boolean isCreateRequest) {
        Employee employee = isCreateRequest ? new Employee() : this.employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException(Employee.class, employeeId));
        return employee;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long employeeId) {
        try {
            var entity = this.employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException(Employee.class, employeeId));
            employeeRepository.delete(entity);
        } catch (Exception ex) {
            log.error("Error while deleting", ex);
            throw ex;
        }
    }

    @Override
    public List<EmployeeDto> getEmployeeHistoryById(Long id, Pageable pageRequest) {
        if (!employeeRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException(Employee.class, id);
        }
        List<EmployeeDto> employeeHistoryDtoList = null;
        try {
            Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), RevisionSort.desc());
            Page<Revision<Long, Employee>> employeeRevisions = employeeRepository.findRevisions(id, pageable);

            employeeHistoryDtoList = employeeRevisions.stream().map((p) ->
                    employeeMapper.toDto(p.getEntity())
            ).collect(Collectors.toList());

        } catch (DataAccessException ex) {
           ex.printStackTrace();
        }
        return employeeHistoryDtoList;
    }
}
