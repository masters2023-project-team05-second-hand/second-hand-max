package team05a.secondhand.errors.exception;

public class ChatRoomNotFoundException extends RuntimeException {

	private static final String MESSAGE = "채팅방을 찾을 수 없습니다.";

	public ChatRoomNotFoundException() {
		super(MESSAGE);
	}
}
