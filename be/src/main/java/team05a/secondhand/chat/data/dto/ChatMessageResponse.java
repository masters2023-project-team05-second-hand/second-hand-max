package team05a.secondhand.chat.data.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.chat.data.entity.Message;

@Getter
public class ChatMessageResponse {

	private final String roomId;
	private final Long senderId;
	private final String message;
	private final Timestamp sentTime;

	@Builder
	private ChatMessageResponse(String roomId, Long senderId, String message, Timestamp sentTime) {
		this.roomId = roomId;
		this.senderId = senderId;
		this.message = message;
		this.sentTime = sentTime;
	}

	public static ChatMessageResponse from(Message message) {
		return ChatMessageResponse.builder()
			.roomId(message.getChatRoomUuid())
			.senderId(message.getSender().getId())
			.message(message.getContent())
			.sentTime(message.getCreatedTime())
			.build();
	}
}
