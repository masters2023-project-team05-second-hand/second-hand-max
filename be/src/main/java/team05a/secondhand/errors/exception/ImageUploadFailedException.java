package team05a.secondhand.errors.exception;

public class ImageUploadFailedException extends RuntimeException {

	private static final String MESSAGE = "이미지 업로드에 실패했습니다.";

	public ImageUploadFailedException() {
		super(MESSAGE);
	}
}
