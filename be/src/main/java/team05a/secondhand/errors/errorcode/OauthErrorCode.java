package team05a.secondhand.errors.errorcode;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum OauthErrorCode implements ErrorCode {

	INVALID_OAUTH_ATTRIBUTES(HttpStatus.BAD_REQUEST, "유효하지 않은 provider 입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	OauthErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	@Override
	public String getName() {
		return name();
	}
}
