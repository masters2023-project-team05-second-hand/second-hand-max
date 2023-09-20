package team05a.secondhand.websocket.chat_room.data.dto;

import lombok.Getter;

@Getter
public class ChatMessageRequest {

	private Long chatRoomId;
	private Long senderId;
	private String message;
}
