
package com.employee.api.v1.model.dto;

import com.employee.ApplicationConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.time.LocalDate;


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
public class AccountDto implements Serializable {
 
    private Long id;

    @XmlElement
    @Email
    private String email;

    @XmlElement
    private String password;

    @XmlTransient
    @DateTimeFormat(pattern = ApplicationConstants.DATE_FORMAT)
    private LocalDate dob;


    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dob=" + dob +
                '}';
    }
}