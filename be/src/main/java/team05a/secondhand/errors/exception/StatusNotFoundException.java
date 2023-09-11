package team05a.secondhand.errors.exception;

public class StatusNotFoundException extends RuntimeException {

	private static final String MESSAGE = "상태를 찾을 수 없습니다.";

	public StatusNotFoundException() {
		super(MESSAGE);
	}
}
