package team05a.secondhand.chat.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.chat.data.entity.ChatRoom;

@Getter
public class ChatRoomUuidResponse {

	@JsonProperty("roomId")
	private final String roomUuid;

	@Builder
	private ChatRoomUuidResponse(String roomUuid) {
		this.roomUuid = roomUuid;
	}

	public static ChatRoomUuidResponse from(ChatRoom chatRoom) {
		if (chatRoom == null) {
			return ChatRoomUuidResponse.builder()
				.roomUuid(null)
				.build();
		}
		return ChatRoomUuidResponse.builder()
			.roomUuid(chatRoom.getUuid())
			.build();
	}
}
