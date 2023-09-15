package team05a.secondhand.errors.handler;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.errorcode.CommonErrorCode;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BindException.class)
	protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
		log.warn("BindException: {}", e.getMessage());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST,
			Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(NumberFormatException.class)
	protected ResponseEntity<ErrorResponse> handleNumberFormatException(NumberFormatException e) {
		log.warn("NumberFormatException: {}", e.getMessage());

		ErrorResponse response = ErrorResponse.of(CommonErrorCode.INVALID_NUMBER);
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
