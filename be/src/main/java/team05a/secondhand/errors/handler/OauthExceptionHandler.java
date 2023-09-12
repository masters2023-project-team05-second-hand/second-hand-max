package team05a.secondhand.errors.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.exception.IllegalOauthAttributesException;

@Slf4j
@RestControllerAdvice
public class OauthExceptionHandler {

	@ExceptionHandler(IllegalOauthAttributesException.class)
	public ResponseEntity<ErrorResponse> handleIllegalOauthAttributesException(IllegalOauthAttributesException e) {
		log.warn("IllegalOauthAttributesException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
