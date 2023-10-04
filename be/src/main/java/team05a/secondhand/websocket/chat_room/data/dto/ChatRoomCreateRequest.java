package team05a.secondhand.websocket.chat_room.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomCreateRequest {

	private final Long productId;

	@Builder
	private ChatRoomCreateRequest(Long productId) {
		this.productId = productId;
	}
}
