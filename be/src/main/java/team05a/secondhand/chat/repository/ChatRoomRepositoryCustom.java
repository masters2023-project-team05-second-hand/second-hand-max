package team05a.secondhand.chat.repository;

import java.util.List;

import team05a.secondhand.chat.data.dto.ChatRoomListResponse;

public interface ChatRoomRepositoryCustom {

	List<ChatRoomListResponse> findChatRoomList(Long memberId, Long productId);
}
