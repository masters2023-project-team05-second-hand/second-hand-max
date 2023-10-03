package team05a.secondhand.chat.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.chat.data.dto.ChatRoomCreateRequest;
import team05a.secondhand.chat.data.dto.ChatRoomUuidResponse;
import team05a.secondhand.chat.data.entity.ChatRoom;
import team05a.secondhand.chat.data.entity.Message;
import team05a.secondhand.chat.repository.ChatRoomRepository;
import team05a.secondhand.chat.repository.MessageRepository;
import team05a.secondhand.errors.exception.MemberNotFoundException;
import team05a.secondhand.errors.exception.ProductNotFoundException;
import team05a.secondhand.errors.exception.SellerIdAndBuyerIdSameException;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRoomRepository chatRoomRepository;
	private final MessageRepository messageRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;

	@Transactional
	public ChatRoomUuidResponse createChatRoom(Long memberId, ChatRoomCreateRequest chatRoomCreateRequest) {
		Member buyer = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
		Product product = productRepository.findById(chatRoomCreateRequest.getProductId())
			.orElseThrow(ProductNotFoundException::new);
		checkBuyerIdAndSellerIdSame(buyer, product);
		String chatRoomUuid = UUID.randomUUID().toString();
		ChatRoom chatRoom = ChatRoom.builder()
			.uuid(chatRoomUuid)
			.buyer(buyer)
			.product(product)
			.build();
		Message message = Message.builder()
			.chatRoomUuid(chatRoomUuid)
			.sender(buyer)
			.content(chatRoomCreateRequest.getMessage().getContent())
			.build();

		ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
		messageRepository.save(message);
		return ChatRoomUuidResponse.builder()
			.roomUuid(savedChatRoom.getUuid())
			.build();
	}

	private void checkBuyerIdAndSellerIdSame(Member buyer, Product product) {
		if (buyer.getId().equals(product.getMember().getId())) {
			throw new SellerIdAndBuyerIdSameException();
		}
	}
}
