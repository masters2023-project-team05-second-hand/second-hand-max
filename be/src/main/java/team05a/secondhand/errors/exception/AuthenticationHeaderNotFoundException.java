package team05a.secondhand.errors.exception;

public class AuthenticationHeaderNotFoundException extends RuntimeException {

	private static final String MESSAGE = "접근 권한 없음: 로그인이 필요합니다.";

	public AuthenticationHeaderNotFoundException() {
		super(MESSAGE);
	}
}
