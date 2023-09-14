package team05a.secondhand.errors.exception;

public class ProductViewNotFoundException extends RuntimeException {

	private static final String MESSAGE = "조회수를 찾을 수 없습니다.";

	public ProductViewNotFoundException() {
		super(MESSAGE);
	}
}
