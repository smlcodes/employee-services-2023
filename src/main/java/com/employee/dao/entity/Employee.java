
package com.employee.dao.entity; 

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.List;

 /**
 * @author Satya Kaveti
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee")
@Accessors(chain = true)
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Employee extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
    @Column 
    private String name;

    @Column 
    private Double salary;

    @Column 
    private String city;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL) 
    private Account account;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "employee_id")
    @NotAudited
    private List<Document> documentList;

}