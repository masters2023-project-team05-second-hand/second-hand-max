package team05a.secondhand.chat.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatMessageCreateRequest {

	private String roomId;
	private Long senderId;
	private String message;

	@Builder
	private ChatMessageCreateRequest(String roomId, Long senderId, String message) {
		this.roomId = roomId;
		this.senderId = senderId;
		this.message = message;
	}
}
