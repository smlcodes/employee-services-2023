package com.employee.support.client;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.ResponseEntity;

/**
 * @author satyakaveti on 26/04/24
 */
public interface ResourceRepository {

     Object getForObject(Resource resource);

     ResponseEntity<?> getForEntity(Resource resource);

     ResponseEntity<?> postForEntity(Resource resource);

     ResponseEntity<?> exchange(Resource resource);

}
