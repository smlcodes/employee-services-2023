package com.employee.api.v1.model.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage implements Serializable {
	
	private String errorCode;
	
	private String errorMessage;
	
	private String detailErrorMessage;
	
	public ErrorMessage(String errorCode, String errorMessage) {
		this.errorCode=errorCode;
		this.errorMessage= errorMessage;
	}
	
	

}
