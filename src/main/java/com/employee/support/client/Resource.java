package com.employee.support.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.net.URI;

/**
 * @author satyakaveti on 26/04/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resource {
    String name;
    URI uri;
    Object data;
    HttpMethod requestType;
    Class<?> responseClass;
}
