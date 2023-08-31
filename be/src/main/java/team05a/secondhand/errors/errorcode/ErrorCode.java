package team05a.secondhand.errors.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	String getName();

	HttpStatus getHttpStatus();

	String getMessage();
}
