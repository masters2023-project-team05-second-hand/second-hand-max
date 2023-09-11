package team05a.secondhand.errors.errorcode;

import org.springframework.http.HttpStatus;

public enum ImageErrorCode implements ErrorCode {

	FILE_SIZE_EXCEED(HttpStatus.BAD_REQUEST, "업로드할 수 있는 파일의 최대 크기는 20MB 입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	ImageErrorCode(HttpStatus httpStatus, String message) {
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
