package team05a.secondhand.websocket.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.errors.exception.MemberNotFoundException;
import team05a.secondhand.errors.exception.ProductNotFoundException;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;
import team05a.secondhand.websocket.chat_message.data.entity.ChatRoom;
import team05a.secondhand.websocket.chat_room.data.dto.ChatMessageRequest;
import team05a.secondhand.websocket.chat_room.data.dto.ChatRoomCreateRequest;
import team05a.secondhand.websocket.chat_room.data.dto.ChatRoomCreateResponse;
import team05a.secondhand.websocket.repository.ChatRoomRepository;

@RequiredArgsConstructor
@Service
public class ChatService {

	private final ChatRoomRepository chatRoomRepository;
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void saveMessage(Long chatRoomId, ChatMessageRequest chatMessageRequest) {

	}

	@Transactional
	public ChatRoomCreateResponse createChatRoom(Long memberId, ChatRoomCreateRequest chatRoomCreateRequest) {
		String roomId = String.valueOf(UUID.randomUUID());
		Product product = productRepository.findById(chatRoomCreateRequest.getProductId())
			.orElseThrow(ProductNotFoundException::new);
		Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
		ChatRoom chatRoom = ChatRoom.builder()
			.product(product)
			.buyer(member)
			.roomId(roomId)
			.build();
		ChatRoom save = chatRoomRepository.save(chatRoom);
		return ChatRoomCreateResponse.from(save);
	}
}
