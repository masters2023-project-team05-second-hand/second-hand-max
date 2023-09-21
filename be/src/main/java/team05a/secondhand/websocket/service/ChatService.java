package team05a.secondhand.websocket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team05a.secondhand.websocket.chat_room.data.dto.ChatMessageRequest;

@Service
public class ChatService {

	@Transactional
	public void saveMessage(Long chatRoomId, ChatMessageRequest chatMessageRequest) {

	}
}
