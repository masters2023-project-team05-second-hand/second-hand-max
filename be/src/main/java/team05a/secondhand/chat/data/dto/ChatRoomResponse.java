package team05a.secondhand.chat.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.member.data.dto.MemberResponse;

@NoArgsConstructor
@Getter
public class ChatRoomResponse {

	private MemberResponse otherMember;
	private ChatRoomLastMessageResponse message;
	private Long unreadMessageCount;

	@Builder
	private ChatRoomResponse(MemberResponse otherMember, ChatRoomLastMessageResponse message, Long unreadMessageCount) {
		this.otherMember = otherMember;
		this.message = message;
		this.unreadMessageCount = unreadMessageCount;
	}

	public static ChatRoomResponse from(MemberResponse otherMember, ChatRoomLastMessageResponse lastMessage,
		Long unreadMessage) {
		return ChatRoomResponse.builder()
			.otherMember(otherMember)
			.message(lastMessage)
			.unreadMessageCount(unreadMessage)
			.build();
	}
}
