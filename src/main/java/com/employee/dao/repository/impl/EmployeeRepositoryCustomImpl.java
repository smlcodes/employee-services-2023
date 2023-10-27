
package com.employee.dao.repository.impl;
import com.employee.api.v1.model.dto.EmployeeSearchDto;
import com.employee.api.v1.model.dto.EmployeeSearchResultsDto;
import com.employee.api.v1.model.mapper.EmployeeSearchResultMapper;
import com.employee.dao.entity.Employee;
import com.employee.dao.entity.QEmployee;
import com.employee.dao.repository.EmployeeRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
 

 
 /**
 * @author Satya Kaveti
 */

@Repository
@Slf4j
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmployeeSearchResultMapper employeeSearchResultMapper;
    
 
    @Override
    public Page<EmployeeSearchResultsDto> employeeSearchCriteria(Pageable pageRequest, EmployeeSearchDto searchCriteria) {

        QEmployee qEmployee = QEmployee.employee;
        BooleanBuilder builder = new BooleanBuilder();
        applySearchCriteria(searchCriteria, qEmployee, builder);

        JPAQuery<Employee> query = new JPAQuery<>(entityManager);
        query = query.from(qEmployee);
        query = query.where(builder);
        Long totalCount = query.fetchCount();
        query.offset(pageRequest.getOffset());
        query.limit(pageRequest.getPageSize());
        PathBuilder<Employee> entityPath = new PathBuilder<>(Employee.class, "employee");

        for (Sort.Order order : pageRequest.getSort()) {
            PathBuilder<Object> path = entityPath.get(order.getProperty());
            query.orderBy(new OrderSpecifier(Order.valueOf(order.getDirection().name()), path));
        }
        List<Employee> result = query.fetch();
        List<EmployeeSearchResultsDto> employeeDtoResult = employeeSearchResultMapper.toEmployeeSearchResultsDtoList(result);
        return new PageImpl<>(employeeDtoResult, pageRequest, totalCount);

    }
 
 
   private void applySearchCriteria(EmployeeSearchDto searchCriteria, QEmployee qEmployee, BooleanBuilder builder) {
        try {

            if (Objects.nonNull(searchCriteria.getName()) && StringUtils.isNotBlank(searchCriteria.getName())) {
                builder.and(qEmployee.name.containsIgnoreCase(searchCriteria.getName()));
            }
            if (Objects.nonNull(searchCriteria.getSalary())) {
                builder.and(qEmployee.salary.eq(searchCriteria.getSalary()));
            }
            if (Objects.nonNull(searchCriteria.getCity()) && StringUtils.isNotBlank(searchCriteria.getCity())) {
                builder.and(qEmployee.city.containsIgnoreCase(searchCriteria.getCity()));
            }       
                } catch (Exception ex) {
            log.error("Exception Occurred while getting search results", ex);

        }
    }

}
