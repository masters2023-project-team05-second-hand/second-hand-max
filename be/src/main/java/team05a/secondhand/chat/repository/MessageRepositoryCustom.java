package team05a.secondhand.chat.repository;

import java.util.List;

import team05a.secondhand.chat.data.dto.MessageReadResponse;

public interface MessageRepositoryCustom {

	List<MessageReadResponse> findChatMessages(Long memberId, String roomId);
}
