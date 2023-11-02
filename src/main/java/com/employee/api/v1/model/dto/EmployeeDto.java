package com.employee.api.v1.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeDto {

    @XmlElement(required = true)
    private Long id;

    @XmlElement(required = true)
    private String name;

    @XmlElement(name = "salary", required = true)
    private Double salary;

    @XmlElement
    private String city;

    @XmlElement(required = true)
    private AccountDto account;

    @XmlElement
    private List<DocumentDto> documentList;


    private String createdBy;
    private String modifiedBy;

    private Date createdDate;
    private Date modifiedDate;

}