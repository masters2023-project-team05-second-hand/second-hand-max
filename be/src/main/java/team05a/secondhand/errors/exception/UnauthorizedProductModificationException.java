package team05a.secondhand.errors.exception;

public class UnauthorizedProductModificationException extends RuntimeException {

	private static final String MESSAGE = "접근 권한이 없는 상품입니다.";

	public UnauthorizedProductModificationException() {
		super(MESSAGE);
	}
}
