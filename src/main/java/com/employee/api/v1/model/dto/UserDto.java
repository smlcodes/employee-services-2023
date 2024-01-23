
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
public class UserDto implements Serializable {
 
    private Long id;

    private String username;

    private String password;

}