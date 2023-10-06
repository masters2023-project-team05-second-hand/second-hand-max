package team05a.secondhand.chat.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.chat.data.entity.ChatRoom;

@Getter
public class ChatRoomIdResponse {

	@JsonProperty("roomId")
	private final String roomUuid;

	@Builder
	private ChatRoomIdResponse(String roomUuid) {
		this.roomUuid = roomUuid;
	}

	public static ChatRoomIdResponse from(ChatRoom chatRoom) {
		if (chatRoom == null) {
			return ChatRoomIdResponse.builder()
				.roomUuid(null)
				.build();
		}
		return ChatRoomIdResponse.builder()
			.roomUuid(chatRoom.getUuid())
			.build();
	}
}
