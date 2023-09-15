package team05a.secondhand.errors.exception;

public class AccessTokenBlacklistException extends RuntimeException {

	private static final String MESSAGE = "블랙리스트에 등록된 액세스 토큰으로는 접근할 수 없습니다.";

	public AccessTokenBlacklistException() {
		super(MESSAGE);
	}
}
