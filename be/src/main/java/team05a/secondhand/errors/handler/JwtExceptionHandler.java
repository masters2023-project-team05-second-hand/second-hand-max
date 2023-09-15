package team05a.secondhand.errors.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.exception.AccessTokenBlacklistException;
import team05a.secondhand.errors.exception.AuthenticationHeaderNotFoundException;
import team05a.secondhand.errors.exception.RefreshTokenNotFoundException;
import team05a.secondhand.errors.exception.TokenMemberMismatchException;

@Slf4j
@RestControllerAdvice
public class JwtExceptionHandler {

	@ExceptionHandler(AccessTokenBlacklistException.class)
	public ResponseEntity<ErrorResponse> handleAccessTokenBlacklistException(AccessTokenBlacklistException e) {
		log.warn("AccessTokenBlacklistException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.UNAUTHORIZED, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(AuthenticationHeaderNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationHeaderNotFoundException(
		AuthenticationHeaderNotFoundException e) {
		log.warn("AuthenticationHeaderNotFoundException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.UNAUTHORIZED, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(RefreshTokenNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException e) {
		log.warn("RefreshTokenNotFoundException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.NOT_FOUND, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(TokenMemberMismatchException.class)
	public ResponseEntity<ErrorResponse> handleTokenMemberMismatchException(TokenMemberMismatchException e) {
		log.warn("TokenMemberMismatchException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.FORBIDDEN, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
