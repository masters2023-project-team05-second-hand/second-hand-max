package team05a.secondhand.chat.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.chat.data.dto.ChatMessageCreateRequest;
import team05a.secondhand.chat.data.dto.ChatMessageResponse;
import team05a.secondhand.chat.service.ChatService;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

	private final ChatService chatService;

	@MessageMapping("/chat/message/{roomId}")
	@SendTo("/chat/room/{roomId}")
	public ChatMessageResponse create(ChatMessageCreateRequest chatMessageCreateRequest,
		@DestinationVariable String roomId) {
		return chatService.createChatMessage(chatMessageCreateRequest, roomId);
	}
}
