package com.employee.service.impl;

import com.employee.api.v1.model.dto.EmailDto;
import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.api.v1.model.dto.EmployeeSearchDto;
import com.employee.api.v1.model.dto.EmployeeSearchResultsDto;
import com.employee.api.v1.model.mapper.EmployeeMapper;
import com.employee.dao.entity.Employee;
import com.employee.dao.repository.EmployeeRepository;
import com.employee.exception.BusinessException;
import com.employee.exception.EntityNotFoundException;
import com.employee.service.EmployeeService;
import com.employee.utils.EmailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionSort;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;


/**
 * @author Satya Kaveti
 */


@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private TaskScheduler taskScheduler;
    private Map<String, ScheduledFuture<?>> scheduledTasks;
    @Autowired
    private EmailUtil emailUtil;

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
    @Cacheable(value = "employees", key = "#id")
    public EmployeeDto getEmployeeById(Long id) {
        var entity = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Employee.class, id));
        return employeeMapper.toDto(entity);
    }

    @Override
    @Cacheable(value = "employees")
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.mapEntityListToDtoListForEmployee(employees);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @CacheEvict(cacheNames = "employees", allEntries = true)
    public void delete(Long employeeId) {
        try {
            var entity = this.employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException(Employee.class, employeeId));
            employeeRepository.delete(entity);
        } catch (Exception ex) {
            log.error("Error while deleting", ex);
            throw ex;
        }
    }

    private Employee getEmployee(Long employeeId, Boolean isCreateRequest) {
        Employee employee = isCreateRequest ? new Employee() : this.employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException(Employee.class, employeeId));
        return employee;
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

    @Override
    public void scheduleTask(String cron) {
//Parameter 2 of constructor in com.employee.service.impl.EmployeeServiceImpl required a bean of type 'org.springframework.scheduling.TaskScheduler' that could not be found.
        //Solution : @EnableScheduling at Application.java
        String taskId = "1";

        if (!CollectionUtils.isEmpty(scheduledTasks)) {
            taskId = (scheduledTasks.size()) + "";
        }

        EmailDto emailDto = getEmailData();
        Runnable task = () -> {
            try {
                emailUtil.sendMail(emailDto);
            } catch (JobExecutionException e) {
                throw new RuntimeException(e);
            }
        };
        ScheduledFuture<?> future = taskScheduler.schedule(task, new CronTrigger(cron));
        scheduledTasks.put(taskId, future);
        log.info("Task ID ", taskId);
    }


    private EmailDto getEmailData() {
        EmailDto emailDto = EmailDto.builder()
                .from("satyakaveti@gmail.com")
                .subject("All Emp Data" + new Date())
                .to(new String[]{"satyakaveti@gmail.com"})
                .cc(new String[]{"satyakaveti@gmail.com"})
                .attachmentLinks(null).build();
        StringBuilder body = new StringBuilder();
        body.append("All Employees data as of ").append(new Date().toString());
        body.append(" ===================================== ");
        List<Employee> emps = employeeRepository.findAll();
        for (Employee emp : emps) {
            EmployeeDto dto = employeeMapper.toDto(emp);
            body.append(dto.toString()).append(" <br>");
        }
        emailDto.setBody(body.toString());
        return emailDto;
    }

}

