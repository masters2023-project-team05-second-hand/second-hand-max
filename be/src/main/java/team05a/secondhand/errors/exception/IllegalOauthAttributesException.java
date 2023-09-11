package team05a.secondhand.errors.exception;

import team05a.secondhand.errors.errorcode.ErrorCode;

public class IllegalOauthAttributesException extends RuntimeException {

	public IllegalOauthAttributesException(ErrorCode errorCode) {
		super(errorCode.getMessage());
	}
}
