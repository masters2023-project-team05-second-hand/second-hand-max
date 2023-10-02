package team05a.secondhand.chat.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.chat.data.dto.ChatRoomCreateRequest;
import team05a.secondhand.chat.data.dto.ChatRoomUuidResponse;
import team05a.secondhand.chat.data.entity.ChatRoom;
import team05a.secondhand.chat.repository.ChatRoomRepository;
import team05a.secondhand.errors.exception.MemberNotFoundException;
import team05a.secondhand.errors.exception.ProductNotFoundException;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRoomRepository chatRoomRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;

	@Transactional
	public ChatRoomUuidResponse createChatRoom(Long memberId, ChatRoomCreateRequest chatRoomCreateRequest) {
		Member buyer = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
		Product product = productRepository.findById(chatRoomCreateRequest.getProductId())
			.orElseThrow(ProductNotFoundException::new);
		String uuid = UUID.randomUUID().toString();
		ChatRoom chatRoom = ChatRoom.builder()
			.uuid(uuid)
			.buyer(buyer)
			.product(product)
			.build();

		ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
		return ChatRoomUuidResponse.builder()
			.roomUuid(savedChatRoom.getUuid())
			.build();
	}

}
