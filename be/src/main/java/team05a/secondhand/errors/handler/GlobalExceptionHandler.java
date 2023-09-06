package team05a.secondhand.errors.handler;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.errorcode.CommonErrorCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BindException.class)
	protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
		logger.warn("BindException: {}", e.getMessage());

		ErrorResponse response = ErrorResponse.from(
			Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(NumberFormatException.class)
	protected ResponseEntity<ErrorResponse> handleNumberFormatException(NumberFormatException e) {
		logger.warn("NumberFormatException: {}", e.getMessage());

		ErrorResponse response = ErrorResponse.of(CommonErrorCode.INVALID_NUMBER);
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
