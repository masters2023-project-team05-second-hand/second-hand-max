package team05a.secondhand.errors.exception;

public class CategoryNotFoundException extends RuntimeException {

	private static final String MESSAGE = "카테고리를 찾을 수 없습니다.";

	public CategoryNotFoundException() {
		super(MESSAGE);
	}
}
