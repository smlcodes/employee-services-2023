
package com.employee.dao.repository;

import com.employee.dao.entity.Employee;
import com.employee.dao.entity.QEmployee;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.history.RevisionRepository;


/**
 * @author Satya Kaveti
 */

public interface EmployeeRepository extends RevisionRepository<Employee, Long, Long>, JpaRepository<Employee, Long>, EmployeeRepositoryCustom,
		QuerydslPredicateExecutor<Employee>,
		QuerydslBinderCustomizer<QEmployee> {

	@Override
	default void customize(QuerydslBindings bindings, QEmployee root) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
}