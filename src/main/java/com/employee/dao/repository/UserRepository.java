
package com.employee.dao.repository;


import com.employee.dao.entity.QUser;
import com.employee.dao.entity.User;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

/**
 * @author Satya Kaveti
 */

public interface UserRepository extends RevisionRepository<User, Long, Long>, JpaRepository<User, Long>,
		QuerydslPredicateExecutor<User>,
		QuerydslBinderCustomizer<QUser> {

	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

    List<User> findFirst10ByOrderById();
}