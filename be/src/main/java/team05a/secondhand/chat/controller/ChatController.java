package team05a.secondhand.chat.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.chat.data.dto.ChatRoomCreateRequest;
import team05a.secondhand.chat.data.dto.ChatRoomUuidResponse;
import team05a.secondhand.chat.service.ChatService;
import team05a.secondhand.member.resolver.MemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

	private final ChatService chatService;

	@PostMapping("/room")
	public ResponseEntity<ChatRoomUuidResponse> createChatRoom(@MemberId Long memberId,
		@Valid @RequestBody ChatRoomCreateRequest chatRoomCreateRequest) {
		ChatRoomUuidResponse chatRoomUuidResponse = chatService.createChatRoom(memberId, chatRoomCreateRequest);

		return ResponseEntity.ok()
			.body(chatRoomUuidResponse);
	}
}
