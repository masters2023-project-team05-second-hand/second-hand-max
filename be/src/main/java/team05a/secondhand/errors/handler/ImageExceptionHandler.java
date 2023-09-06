package team05a.secondhand.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.errorcode.ImageErrorCode;
import team05a.secondhand.errors.exception.ImageCountOutOfRangeException;
import team05a.secondhand.errors.exception.ImageUploadFailedException;

@RestControllerAdvice
public class ImageExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ImageExceptionHandler.class);

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	protected ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(
		MaxUploadSizeExceededException e) {
		logger.warn("handleMaxUploadSizeExceededException", e);

		ErrorResponse response = ErrorResponse.of(ImageErrorCode.FILE_SIZE_EXCEED);
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(ImageCountOutOfRangeException.class)
	public ResponseEntity<ErrorResponse> handleImageCountOutOfRangeException(ImageCountOutOfRangeException e) {
		logger.warn("ImageCountOutOfRangeException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(ImageUploadFailedException.class)
	public ResponseEntity<ErrorResponse> handleImageUploadFailedException(ImageUploadFailedException e) {
		logger.warn("ImageUploadFailedException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
