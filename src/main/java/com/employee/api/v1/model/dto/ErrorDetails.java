package com.employee.api.v1.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
@Builder
public class ErrorDetails {
  
  private HttpStatus code;

  private Object message;

}
