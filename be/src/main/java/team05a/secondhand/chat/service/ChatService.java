package team05a.secondhand.chat.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.chat.data.dto.ChatMessageCreateRequest;
import team05a.secondhand.chat.data.dto.ChatMessageResponse;
import team05a.secondhand.chat.data.dto.ChatRoomCreateRequest;
import team05a.secondhand.chat.data.dto.ChatRoomCreateResponse;
import team05a.secondhand.chat.data.dto.ChatRoomIdResponse;
import team05a.secondhand.chat.data.dto.ChatRoomListResponse;
import team05a.secondhand.chat.data.dto.MessageReadResponse;
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
	public ChatRoomCreateResponse createChatRoom(Long buyerId, ChatRoomCreateRequest chatRoomCreateRequest) {
		Member buyer = memberRepository.findById(buyerId).orElseThrow(MemberNotFoundException::new);
		Product product = productRepository.findById(chatRoomCreateRequest.getProductId())
			.orElseThrow(ProductNotFoundException::new);
		checkChatRoomCreationAllowed(buyer, product);
		ChatRoom chatRoom = ChatRoom.builder()
			.buyer(buyer)
			.product(product)
			.seller(product.getMember())
			.build();
		Message message = Message.builder()
			.chatRoomUuid(chatRoom.getUuid())
			.sender(buyer)
			.content(chatRoomCreateRequest.getMessage())
			.build();

		ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
		Message savedmessage = messageRepository.save(message);
		return ChatRoomCreateResponse.from(savedChatRoom, savedmessage.getCreatedTime());
	}

	@Transactional(readOnly = true)
	public ChatRoomIdResponse readChatRoom(Long buyerId, Long productId) {
		ChatRoom chatRoom = chatRoomRepository.findByBuyerIdAndProductId(buyerId, productId)
			.orElse(null);

		return ChatRoomIdResponse.from(chatRoom);
	}

	@Transactional
	public ChatMessageResponse createChatMessage(ChatMessageCreateRequest chatMessageCreateRequest, String roomId) {
		Member sender = memberRepository.findById(chatMessageCreateRequest.getSenderId())
			.orElseThrow(MemberNotFoundException::new);
		Message message = Message.builder()
			.chatRoomUuid(roomId)
			.sender(sender)
			.content(chatMessageCreateRequest.getMessage())
			.build();

		Message savedMessage = messageRepository.save(message);
		return ChatMessageResponse.from(savedMessage);
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

	@Transactional(readOnly = true)
	public List<ChatRoomListResponse> readChatRoomList(Long memberId, Long productId) {
		List<ChatRoomListResponse> chatRoomList = chatRoomRepository.findChatRoomList(memberId, productId);
		Collections.sort(chatRoomList);
		return chatRoomList;
	}

	@Transactional
	public List<MessageReadResponse> readChatMessages(Long memberId, String roomId) {
		return messageRepository.findChatMessages(memberId, roomId);
	}

	@Transactional
	public void exitChatRoom(Long memberId, String roomId) {
		ChatRoom chatRoom = chatRoomRepository.findByUuid(roomId);
		if (memberId.equals(chatRoom.getProduct().getMember().getId())) {
			chatRoom.deleteSeller();
		}
		if (memberId.equals(chatRoom.getBuyer().getId())) {
			chatRoom.deleteBuyer();
		}
	}
}
