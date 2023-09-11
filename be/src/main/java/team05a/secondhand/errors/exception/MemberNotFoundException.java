package team05a.secondhand.errors.exception;

public class MemberNotFoundException extends RuntimeException {

	private static final String MESSAGE = "멤버를 찾을 수 없습니다.";

	public MemberNotFoundException() {
		super(MESSAGE);
	}
}
