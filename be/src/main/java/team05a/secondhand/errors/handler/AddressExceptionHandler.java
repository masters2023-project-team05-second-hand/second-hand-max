package team05a.secondhand.errors.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.exception.AddressNotFoundException;

@Slf4j
@RestControllerAdvice
public class AddressExceptionHandler {

	@ExceptionHandler(AddressNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException e) {
		log.warn("AddressNotFoundException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
