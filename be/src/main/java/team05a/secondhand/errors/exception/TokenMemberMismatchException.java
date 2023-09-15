package team05a.secondhand.errors.exception;

public class TokenMemberMismatchException extends RuntimeException {

	private static final String MESSAGE = "액세스 토큰과 리프레시 토큰의 사용자 정보가 일치하지 않습니다.";

	public TokenMemberMismatchException() {
		super(MESSAGE);
	}
}
