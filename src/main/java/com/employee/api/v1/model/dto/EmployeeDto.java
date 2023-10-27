
package com.employee.api.v1.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * @author Satya Kaveti
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
 
    private Long id;

    private String name;

    private Double salary;

    private String city;

    private AccountDto account;

    private List<DocumentDto> documentList;


    private String createdBy;
    private String modifiedBy;

    private Date createdDate;
    private Date modifiedDate;

}