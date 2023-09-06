package team05a.secondhand.errors.exception;

public class AddressNotFoundException extends RuntimeException {

	private static final String MESSAGE = "주소를 찾을 수 없습니다.";

	public AddressNotFoundException() {
		super(MESSAGE);
	}
}
