
package com.employee.dao.entity; 

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import com.employee.ApplicationConstants;
 
 /**
 * @author Satya Kaveti
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
@Accessors(chain = true)
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
    @Column 
    private String email;

    @Column 
    private String password;

    @DateTimeFormat(pattern = ApplicationConstants.DATE_FORMAT) 
    private LocalDate dob;

}