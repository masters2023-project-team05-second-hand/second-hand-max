package team05a.secondhand.chat.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatRoomListResponse implements Comparable<ChatRoomListResponse> {

	private ChatRoomResponse chatRoom;

	private String productThumbnailUrl;

	@Builder
	private ChatRoomListResponse(ChatRoomResponse chatRoom, String productThumbnailUrl) {
		this.chatRoom = chatRoom;
		this.productThumbnailUrl = productThumbnailUrl;
	}

	public static ChatRoomListResponse from(ChatRoomResponse chatRoomResponse, String thumbnailUrl) {
		return ChatRoomListResponse.builder()
			.chatRoom(chatRoomResponse)
			.productThumbnailUrl(thumbnailUrl)
			.build();
	}

	@Override
	public int compareTo(ChatRoomListResponse o) {
		return o.getChatRoom().getMessage().getLastSentTime().compareTo(this.getChatRoom().getMessage()
			.getLastSentTime());
	}
}
