package team05a.secondhand.chat.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomUuidResponse {

	@JsonProperty("roomId")
	private final String roomUuid;

	@Builder
	private ChatRoomUuidResponse(String roomUuid) {
		this.roomUuid = roomUuid;
	}
}
