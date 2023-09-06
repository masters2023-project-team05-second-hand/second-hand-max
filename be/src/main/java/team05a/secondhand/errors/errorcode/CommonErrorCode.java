package team05a.secondhand.errors.errorcode;

import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCode {

	INVALID_NUMBER(HttpStatus.BAD_REQUEST, "숫자가 아닙니다.");

	private final HttpStatus httpStatus;
	private final String message;

	CommonErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	@Override
	public String getName() {
		return name();
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
