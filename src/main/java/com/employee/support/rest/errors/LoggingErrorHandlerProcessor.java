package com.employee.support.rest.errors;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.alidg.errors.HttpError;
import me.alidg.errors.WebErrorHandlerPostProcessor;

/**
 * Register ErrorHandler Post Processor to log error responses.
 * <p>
 * Following Rules apply:
 * <ol>
 * <li> Client Errors 4xx are logged at a WARN Level </li>
 * <li> Server Errors 5xx are logged at a ERROR level </li>
 * </ol>
 * <p>
 * If fingerprint is enable, then logs can be matched with client received errors.
 * 
 * @author Satya
 */
@Component
@Slf4j
public class LoggingErrorHandlerProcessor implements WebErrorHandlerPostProcessor {

	@Override
	public void process(HttpError error) {
		if (error.getHttpStatus().is4xxClientError()) {
			log.warn("REST API Response Error -> [Fingerprint: {}]\n\tStatus: {}\n\tURL: {}\n\tException: {}: {}\n\tPayload: {}", 
					error.getFingerprint(), 
					error.getHttpStatus(),
					error.getRequest(),
					error.getOriginalException().getClass().getName(), 
					error.getOriginalException().getMessage(),
					error.getErrors()
			);
		} else if (error.getHttpStatus().is5xxServerError()) {
			log.error("REST API Response Error -> [Fingerprint: {}]\n\tStatus: {}\n\tURL: {}\n\tPayload: {}", 
					error.getFingerprint(), 
					error.getHttpStatus(),
					error.getRequest(),
					error.getErrors(),
					error.getOriginalException()
			);
		}
	}
}
