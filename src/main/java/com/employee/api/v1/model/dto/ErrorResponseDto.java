package com.employee.api.v1.model.dto;

import lombok.Data;

@Data
public class ErrorResponseDto {
	/*
	 * Error Section: Denotes in which section of the original message the error
	 * occurred. 1 = Heading section 2 = Detail section 6 = Summary section
	 */
	private String errorSection;

	/*
	 * Line Number: The relevant line number from the original document on which the
	 * error occurred.
	 */
	private String lineNo;

	/* Error Number : Error description in coded form. */
	private String errorCode;

	private String errorDesc;
}
