package team05a.secondhand.errors.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.exception.ProductNotFoundException;
import team05a.secondhand.errors.exception.ProductViewNotFoundException;
import team05a.secondhand.errors.exception.UnauthorizedProductModificationException;

@Slf4j
@RestControllerAdvice
public class ProductExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e) {
		log.warn("ProductNotFoundException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(UnauthorizedProductModificationException.class)
	public ResponseEntity<ErrorResponse> handleUnauthorizedProductModificationException(
		UnauthorizedProductModificationException e) {
		log.warn("UnauthorizedProductModificationException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(ProductViewNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductViewNotFoundException(ProductViewNotFoundException e) {
		log.warn("ProductViewNotFoundException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
