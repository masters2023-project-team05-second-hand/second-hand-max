package team05a.secondhand.errors.exception;

public class RefreshTokenNotFoundException extends RuntimeException {

	private static final String MESSAGE = "일치하는 리프레시 토큰을 찾을 수 없습니다.";

	public RefreshTokenNotFoundException() {
		super(MESSAGE);
	}
}
