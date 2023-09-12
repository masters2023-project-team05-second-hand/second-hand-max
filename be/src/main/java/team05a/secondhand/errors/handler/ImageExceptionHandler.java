package team05a.secondhand.errors.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.errorcode.ImageErrorCode;
import team05a.secondhand.errors.exception.ImageCountOutOfRangeException;
import team05a.secondhand.errors.exception.ImageNotFoundException;
import team05a.secondhand.errors.exception.ImageUploadFailedException;

@Slf4j
@RestControllerAdvice
public class ImageExceptionHandler {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	protected ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(
		MaxUploadSizeExceededException e) {
		log.warn("handleMaxUploadSizeExceededException", e);

		ErrorResponse response = ErrorResponse.of(ImageErrorCode.FILE_SIZE_EXCEED);
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(ImageCountOutOfRangeException.class)
	public ResponseEntity<ErrorResponse> handleImageCountOutOfRangeException(ImageCountOutOfRangeException e) {
		log.warn("ImageCountOutOfRangeException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(ImageUploadFailedException.class)
	public ResponseEntity<ErrorResponse> handleImageUploadFailedException(ImageUploadFailedException e) {
		log.warn("ImageUploadFailedException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(ImageNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleImageNotFoundException(ImageNotFoundException e) {
		log.warn("ImageNotFoundException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
