package com.employee.api.v1.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
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
public class EmployeeDto implements Serializable {



    @XmlElement(required = true)
    private Long id;

    @XmlElement(required = true)
    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    @XmlElement(name = "salary", required = true)
    @Digits(integer = 10, fraction = 2)
    private Double salary;

    @XmlElement
    @Size(max = 20)
    private String city;

    @XmlElement(required = true)
    private AccountDto account;

    @XmlElement
    private List<DocumentDto> documentList;


    private String createdBy;
    private String modifiedBy;

    private Date createdDate;
    private Date modifiedDate;


    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", city='" + city + '\'' +
                ", account=" + account +
                ", documentList=" + documentList +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }


}