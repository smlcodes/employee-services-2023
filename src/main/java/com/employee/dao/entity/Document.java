
package com.employee.dao.entity; 

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
 /**
 * @author Satya Kaveti
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "document")
@Accessors(chain = true)
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Document { 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
    @Column 
    private String proofType;

    @Column 
    private String docName;

    @Column 
    private String docUrl;

}