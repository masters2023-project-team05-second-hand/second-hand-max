package team05a.secondhand.errors.exception;

public class IllegalChatRoomParticipantException extends RuntimeException {

	private static final String MESSAGE = "채팅방에 존재하는 유저가 아닙니다.";

	public IllegalChatRoomParticipantException() {
		super(MESSAGE);
	}
}
