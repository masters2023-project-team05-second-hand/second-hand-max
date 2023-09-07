package team05a.secondhand.errors.exception;

public class ImageNotFoundException extends RuntimeException {

	private static final String MESSAGE = "상품 이미지를 찾을 수 없습니다.";

	public ImageNotFoundException() {
		super(MESSAGE);
	}
}
