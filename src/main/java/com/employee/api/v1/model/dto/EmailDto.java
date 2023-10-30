package com.employee.api.v1.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author satyakaveti
 */
@Data
@Builder
public class EmailDto {
    String from;
    String subject;
    String body;
    String[] to;
    String[] cc;
    String[] attachmentLinks;
}
