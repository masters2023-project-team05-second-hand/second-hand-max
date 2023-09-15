package team05a.secondhand.errors;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import team05a.secondhand.errors.errorcode.ErrorCode;

@Getter
public class ErrorResponse {

	private final int code;
	private final HttpStatus status;
	private final String message;

	public ErrorResponse(int code, HttpStatus status, String message) {
		this.code = code;
		this.status = status;
		this.message = message;
	}

	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode.getHttpStatus().value(), errorCode.getHttpStatus(), errorCode.getMessage());
	}

	public static ErrorResponse from(HttpStatus httpStatus, String message) {
		return new ErrorResponse(httpStatus.value(), httpStatus, message);
	}
}
