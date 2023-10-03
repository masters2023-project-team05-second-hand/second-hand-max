package team05a.secondhand.errors.exception;

public class BuyerIdAndMessageSenderIdNotSameException extends RuntimeException {

	private static final String MESSAGE = "구매자 아이디와 첫 메세지 발송자 아이디가 일치하지 않습니다.";

	public BuyerIdAndMessageSenderIdNotSameException() {
		super(MESSAGE);
	}
}
