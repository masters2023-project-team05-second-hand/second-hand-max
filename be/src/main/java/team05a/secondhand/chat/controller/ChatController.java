package team05a.secondhand.chat.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.chat.data.dto.ChatRoomCreateRequest;
import team05a.secondhand.chat.data.dto.ChatRoomCreateResponse;
import team05a.secondhand.chat.data.dto.ChatRoomIdResponse;
import team05a.secondhand.chat.data.dto.ChatRoomListResponse;
import team05a.secondhand.chat.data.dto.MessageReadResponse;
import team05a.secondhand.chat.service.ChatService;
import team05a.secondhand.member.resolver.MemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-room")
public class ChatController {

	private final ChatService chatService;

	@PostMapping
	public ResponseEntity<ChatRoomCreateResponse> createChatRoom(@MemberId Long buyerId,
		@Valid @RequestBody ChatRoomCreateRequest chatRoomCreateRequest) {
		ChatRoomCreateResponse chatRoomCreateResponse = chatService.createChatRoom(buyerId, chatRoomCreateRequest);

		return ResponseEntity.ok()
			.body(chatRoomCreateResponse);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ChatRoomIdResponse> readChatRoom(@MemberId Long buyerId,
		@PathVariable Long productId) {
		ChatRoomIdResponse chatRoomIdResponse = chatService.readChatRoom(buyerId, productId);

		return ResponseEntity.ok()
			.body(chatRoomIdResponse);
	}

	@GetMapping
	public ResponseEntity<List<ChatRoomListResponse>> readChatRoomList(@MemberId Long memberId,
		@RequestParam(defaultValue = "0") Long productId) {
		List<ChatRoomListResponse> chatRoomListResponse = chatService.readChatRoomList(memberId, productId);

		return ResponseEntity.ok()
			.body(chatRoomListResponse);
	}

	@GetMapping("/messages")
	public ResponseEntity<List<MessageReadResponse>> readChatMessages(@MemberId Long memberId,
		@RequestParam(defaultValue = "null") String roomId) {
		List<MessageReadResponse> messageReadResponseList = chatService.readChatMessages(memberId, roomId);

		return ResponseEntity.ok()
			.body(messageReadResponseList);
	}

	@DeleteMapping("/{roomId}")
	public ResponseEntity<Void> exitChatRoom(@MemberId Long memberId, @PathVariable String roomId) {
		chatService.exitChatRoom(memberId, roomId);

		return ResponseEntity.ok().build();
	}
}
