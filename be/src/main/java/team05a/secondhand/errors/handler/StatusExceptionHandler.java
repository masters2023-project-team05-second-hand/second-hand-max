package team05a.secondhand.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.exception.StatusNotFoundException;

@RestControllerAdvice
public class StatusExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(StatusExceptionHandler.class);

	@ExceptionHandler(StatusNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleStatusNotFoundException(StatusNotFoundException e) {
		logger.warn("StatusNotFoundException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
