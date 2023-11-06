package com.employee.api.v1.model.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlTransient
public class ErrorDetails {

  private HttpStatus code;

  private List<String> message;

}
