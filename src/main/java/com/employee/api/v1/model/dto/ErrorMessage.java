package com.employee.api.v1.model.dto;

import lombok.*;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlTransient
public class ErrorMessage implements Serializable {
	
	private String errorCode;
	
	private String errorMessage;
	
	private String detailErrorMessage;
	
	public ErrorMessage(String errorCode, String errorMessage) {
		this.errorCode=errorCode;
		this.errorMessage= errorMessage;
	}
	
	

}
