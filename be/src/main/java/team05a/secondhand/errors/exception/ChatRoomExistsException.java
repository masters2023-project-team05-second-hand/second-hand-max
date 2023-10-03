package team05a.secondhand.errors.exception;

public class ChatRoomExistsException extends RuntimeException {

	private static final String MESSAGE = "해당 제품 아이디와 구매자 아이디로 채팅방이 이미 존재합니다.";

	public ChatRoomExistsException() {
		super(MESSAGE);
	}
}
