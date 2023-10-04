package team05a.secondhand.websocket.chat_room.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.websocket.chat_message.data.entity.ChatRoom;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomCreateResponse {

	private String roomId;

	@Builder
	private ChatRoomCreateResponse(String roomId) {
		this.roomId = roomId;
	}

	public static ChatRoomCreateResponse from(ChatRoom chatRoom) {
		return ChatRoomCreateResponse.builder()
			.roomId(chatRoom.getRoomId())
			.build();
	}
}
