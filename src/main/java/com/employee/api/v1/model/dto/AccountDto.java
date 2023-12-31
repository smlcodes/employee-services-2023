
package com.employee.api.v1.model.dto; 
 
import com.employee.ApplicationConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.xml.bind.annotation.*;
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
public class AccountDto {
 
    private Long id;

    @XmlElement
    @Email
    private String email;

    @XmlElement
    private String password;

    @XmlTransient
    @DateTimeFormat(pattern = ApplicationConstants.DATE_FORMAT)
    private LocalDate dob;

}