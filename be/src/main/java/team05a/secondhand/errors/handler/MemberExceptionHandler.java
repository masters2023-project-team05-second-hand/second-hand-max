package team05a.secondhand.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.exception.MemberNotFoundException;

@RestControllerAdvice
public class MemberExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(MemberExceptionHandler.class);

	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleMemberNotFoundException(MemberNotFoundException e) {
		logger.warn("MemberNotFoundException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
