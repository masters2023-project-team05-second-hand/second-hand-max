package team05a.secondhand.errors.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.exception.CategoryNotFoundException;

@Slf4j
@RestControllerAdvice
public class CategoryExceptionHandler {

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException e) {
		log.warn("CategoryNotFoundException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
