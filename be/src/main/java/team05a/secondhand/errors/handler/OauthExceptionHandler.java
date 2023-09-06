package team05a.secondhand.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.exception.IllegalOauthAttributesException;

@RestControllerAdvice
public class OauthExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(OauthExceptionHandler.class);

	@ExceptionHandler(IllegalOauthAttributesException.class)
	public ResponseEntity<ErrorResponse> handleIllegalOauthAttributesException(IllegalOauthAttributesException e) {
		logger.warn("IllegalOauthAttributesException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
