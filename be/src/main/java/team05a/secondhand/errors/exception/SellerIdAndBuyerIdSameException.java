package team05a.secondhand.errors.exception;

public class SellerIdAndBuyerIdSameException extends RuntimeException {

	private static final String MESSAGE = "구매자 아이디와 판매자 아이디가 일치합니다.";

	public SellerIdAndBuyerIdSameException() {
		super(MESSAGE);
	}
}
