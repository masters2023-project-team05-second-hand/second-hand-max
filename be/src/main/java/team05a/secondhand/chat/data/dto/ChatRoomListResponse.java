package team05a.secondhand.chat.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.chat.data.entity.ChatRoom;
import team05a.secondhand.member.data.dto.MemberResponse;

@NoArgsConstructor
@Getter
public class ChatRoomListResponse implements Comparable<ChatRoomListResponse> {

	private String roomId;
	private MemberResponse otherMember;
	private ChatRoomLastMessageResponse message;
	private Long unreadMessageCount;
	private ChatProductResponse product;

	@Builder
	private ChatRoomListResponse(String roomId, MemberResponse otherMember, ChatRoomLastMessageResponse message,
		Long unreadMessageCount, ChatProductResponse product) {
		this.roomId = roomId;
		this.otherMember = otherMember;
		this.message = message;
		this.unreadMessageCount = unreadMessageCount;
		this.product = product;
	}

	public static ChatRoomListResponse from(ChatRoom chatRoom, MemberResponse otherMember,
		ChatRoomLastMessageResponse lastMessage, Long unreadMessage) {
		return ChatRoomListResponse.builder()
			.roomId(chatRoom.getUuid())
			.otherMember(otherMember)
			.message(lastMessage)
			.unreadMessageCount(unreadMessage)
			.product(ChatProductResponse.from(chatRoom.getProduct()))
			.build();
	}

	@Override
	public int compareTo(ChatRoomListResponse o) {
		return o.getMessage().getLastSentTime().compareTo(this.getMessage().getLastSentTime());
	}
}
