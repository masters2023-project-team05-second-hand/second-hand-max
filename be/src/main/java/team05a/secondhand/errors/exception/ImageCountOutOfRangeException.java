package team05a.secondhand.errors.exception;

public class ImageCountOutOfRangeException extends RuntimeException {

	private static final String MESSAGE = "이미지 개수는 1개 이상 10개 이하여야 합니다.";

	public ImageCountOutOfRangeException() {
		super(MESSAGE);
	}
}
