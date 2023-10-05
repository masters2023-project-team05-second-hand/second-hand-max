package team05a.secondhand.chat.data.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.chat.data.entity.ChatRoom;

@Getter
public class ChatRoomCreateResponse {

	@JsonProperty("roomId")
	private final String roomUuid;
	private final Timestamp sentTime;

	@Builder
	private ChatRoomCreateResponse(String roomUuid, Timestamp sentTime) {
		this.roomUuid = roomUuid;
		this.sentTime = sentTime;
	}

	public static ChatRoomCreateResponse from(ChatRoom chatRoom, Timestamp sentTime) {
		return ChatRoomCreateResponse.builder()
			.roomUuid(chatRoom.getUuid())
			.sentTime(sentTime)
			.build();
	}
}
