package team05a.secondhand.chat.data.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.chat.data.entity.Message;

@NoArgsConstructor
@Getter
public class ChatRoomLastMessageResponse {

	private String lastMessage;
	private Timestamp lastSentTime;

	@Builder
	private ChatRoomLastMessageResponse(String lastMessage, Timestamp lastSentTime) {
		this.lastMessage = lastMessage;
		this.lastSentTime = lastSentTime;
	}

	public static ChatRoomLastMessageResponse from(Message lastMessage) {
		return ChatRoomLastMessageResponse.builder()
			.lastMessage(lastMessage.getContent())
			.lastSentTime(lastMessage.getCreatedTime())
			.build();
	}
}
