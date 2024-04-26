package com.employee.support.client;

import com.employee.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @author satyakaveti on 26/04/24
 */
@Component
@Slf4j
public class RestResourceRepository implements ResourceRepository {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Object getForObject(Resource resource) {
        Object response = restTemplate.getForObject(resource.getUri(), resource.responseClass);
        return response;
    }

    @Override
    public ResponseEntity<?> getForEntity(Resource resource) {
        ResponseEntity responseEntity = restTemplate.getForEntity(resource.getUri(), resource.responseClass);
        return responseEntity;
    }

    @Override
    public ResponseEntity<?> postForEntity(Resource resource) {
        try {
            ResponseEntity responseEntity = restTemplate.postForEntity(resource.getUri(), resource.getData(), resource.responseClass);
            return responseEntity;
        } catch (Exception e) {
            log.error("Error: ", e);
            throw new BusinessException("RestTemplate call failed");
        }
    }

    @Override
    public ResponseEntity<?> exchange(Resource resource) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        //headers.add("Authorization", String.format("Bearer %s", accessToken));
        HttpEntity<Object> request = new HttpEntity<>(resource.getData(), headers);
        ResponseEntity response = restTemplate.exchange(resource.getUri(), resource.requestType, request, resource.responseClass);
        return response;
    }
}
