package team05a.secondhand.chat.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatMessageCreateRequest {

	private Long senderId;
	private String message;

	@Builder
	private ChatMessageCreateRequest(Long senderId, String message) {
		this.senderId = senderId;
		this.message = message;
	}

}
