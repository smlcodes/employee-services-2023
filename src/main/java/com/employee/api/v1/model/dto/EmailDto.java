package com.employee.api.v1.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlTransient;

/**
 * @author satyakaveti
 */
@Data
@Builder
@XmlTransient
public class EmailDto {
    String from;
    String subject;
    String body;
    String[] to;
    String[] cc;
    String[] attachmentLinks;
}
