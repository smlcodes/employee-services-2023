package com.employee.api.v1.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * @author satyakaveti
 */
@Data
@Builder
@XmlTransient
public class EmailDto implements Serializable {
    String from;
    String subject;
    String body;
    String[] to;
    String[] cc;
    String[] attachmentLinks;
}
