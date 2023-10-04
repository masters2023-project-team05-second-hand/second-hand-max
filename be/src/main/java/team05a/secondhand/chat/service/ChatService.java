package team05a.secondhand.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.chat.data.dto.ChatRoomCreateRequest;
import team05a.secondhand.chat.data.dto.ChatRoomUuidResponse;
import team05a.secondhand.chat.data.entity.ChatRoom;
import team05a.secondhand.chat.data.entity.Message;
import team05a.secondhand.chat.repository.ChatRoomRepository;
import team05a.secondhand.chat.repository.MessageRepository;
import team05a.secondhand.errors.exception.ChatRoomExistsException;
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
	public ChatRoomUuidResponse createChatRoom(Long buyerId, ChatRoomCreateRequest chatRoomCreateRequest) {
		Member buyer = memberRepository.findById(buyerId).orElseThrow(MemberNotFoundException::new);
		Product product = productRepository.findById(chatRoomCreateRequest.getProductId())
			.orElseThrow(ProductNotFoundException::new);
		checkChatRoomCreationAllowed(buyer, product);
		ChatRoom chatRoom = ChatRoom.builder()
			.buyer(buyer)
			.product(product)
			.build();
		Message message = Message.builder()
			.chatRoomUuid(chatRoom.getUuid())
			.sender(buyer)
			.content(chatRoomCreateRequest.getMessage())
			.build();

		ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
		messageRepository.save(message);
		return ChatRoomUuidResponse.from(savedChatRoom);
	}

	@Transactional(readOnly = true)
	public ChatRoomUuidResponse readChatRoom(Long buyerId, Long productId) {
		ChatRoom chatRoom = chatRoomRepository.findByBuyerIdAndProductId(buyerId, productId)
			.orElse(null);

		return ChatRoomUuidResponse.from(chatRoom);
	}

	private void checkChatRoomCreationAllowed(Member buyer, Product product) {
		checkBuyerIdAndSellerIdSame(buyer, product);
		checkChatRoomExists(buyer, product);
	}

	private void checkBuyerIdAndSellerIdSame(Member buyer, Product product) {
		if (buyer.getId().equals(product.getMember().getId())) {
			throw new SellerIdAndBuyerIdSameException();
		}
	}

	private void checkChatRoomExists(Member buyer, Product product) {
		if (chatRoomRepository.existsByBuyerIdAndProductId(buyer.getId(), product.getId())) {
			throw new ChatRoomExistsException();
		}
	}
}
