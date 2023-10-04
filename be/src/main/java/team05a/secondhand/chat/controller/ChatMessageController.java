package team05a.secondhand.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.chat.data.dto.ChatMessageCreateRequest;
import team05a.secondhand.chat.data.dto.ChatMessageResponse;
import team05a.secondhand.chat.service.ChatService;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

	private final ChatService chatService;
	private final SimpMessageSendingOperations sendingOperations;

	@MessageMapping("/chat/message")
	public void create(ChatMessageCreateRequest chatMessageCreateRequest) {
		ChatMessageResponse chatMessageResponse = chatService.createChatMessage(chatMessageCreateRequest);

		sendingOperations.convertAndSend("/sub/chat/room/" + chatMessageResponse.getRoomId(),
			chatMessageResponse);
	}
}
