package com.reactive.platform.advice;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import com.platform.exceptions.CustomBaseException;
import com.platform.exceptions.CustomBaseRuntimeException;
import com.platform.logging.AuditOperation;
import com.platform.logging.Log;
import com.platform.messages.ErrorResponse;
import com.platform.server.BaseSession;
import com.platform.service.AuditService;

/**
 * @author Muhil Kennedy 
 * Global controller errorhandling
 */
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalReactiveControllerAdvice extends ResponseEntityExceptionHandler {
	
	@Autowired
	private AuditService auditService;

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleSQLIntegrityException(DataIntegrityViolationException ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(),
				"Oops! There is a data conflict! Please try again!"); // TODO: Localize
		Log.platform.error(errorResponse.toString());
		logger.error("SQLIntegrityConstraintViolationException :: Exception :: ", ex);
		auditService.logAudit(BaseSession.getTenant(), BaseSession.getUser(), ExceptionUtils.getStackTrace(ex), AuditOperation.API_RESPONSE, ex, errorResponse.getErrorCode());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		ErrorResponse errorResponse = null;
		if (ex instanceof CustomBaseException cbe) {
			errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), extractMessage(ex),
					cbe.getErrorCode());
			Log.platform.error(errorResponse.toString());
			logger.error("handleGenericException :: Exception :: {}", ex);
			auditService.logAudit(BaseSession.getTenant(), BaseSession.getUser(), ExceptionUtils.getStackTrace(ex),
					AuditOperation.API_RESPONSE, ex, cbe.getErrorCode());
		} else if (ex instanceof CustomBaseRuntimeException cbre) {
			errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), extractMessage(ex),
					cbre.getErrorCode());
			Log.platform.error(errorResponse.toString());
			logger.error("handleGenericException :: Exception :: {}", ex);
			auditService.logAudit(BaseSession.getTenant(), BaseSession.getUser(), ExceptionUtils.getStackTrace(ex),
					AuditOperation.API_RESPONSE, ex, cbre.getErrorCode());
		} else {
			errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), extractMessage(ex));
			Log.platform.error(errorResponse.toString());
			logger.error("handleGenericException :: Exception :: {}", ex);
			auditService.logAudit(BaseSession.getTenant(), BaseSession.getUser(), ExceptionUtils.getStackTrace(ex),
					AuditOperation.API_RESPONSE, ex, errorResponse.getErrorCode());
		}
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private String extractMessage(Exception ex) {
		if (!StringUtils.isAllBlank(ex.getMessage())) {
			return ex.getMessage();
		} else if (ex.getCause() != null) {
			return ex.getCause().getMessage();
		} else {
			return ex.toString();
		}
	}

}
