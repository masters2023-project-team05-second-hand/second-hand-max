package team05a.secondhand.chat.repository;

import static team05a.secondhand.chat.data.entity.QChatRoom.*;
import static team05a.secondhand.chat.data.entity.QMessage.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.chat.data.dto.ChatRoomLastMessageResponse;
import team05a.secondhand.chat.data.dto.ChatRoomListResponse;
import team05a.secondhand.chat.data.entity.ChatRoom;
import team05a.secondhand.chat.data.entity.Message;
import team05a.secondhand.member.data.dto.MemberResponse;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<ChatRoomListResponse> findChatRoomList(Long memberId, Long productId) {
		List<ChatRoom> chatRoomList = jpaQueryFactory
			.selectFrom(chatRoom)
			.where(
				chatRoom.seller.id.eq(memberId).or(
					chatRoom.buyer.id.eq(memberId)),
				eqProductId(productId)
			)
			.fetch();

		return chatRoomList.stream()
			.map(chatRoom -> ChatRoomListResponse.from(chatRoom, getOtherMember(memberId, chatRoom),
				getLastMessage(chatRoom.getUuid()), getUnreadMessage(memberId, chatRoom)))
			.collect(Collectors.toList());
	}

	private MemberResponse getOtherMember(Long memberId, ChatRoom chatRoom) {
		return Objects.equals(memberId, chatRoom.getBuyer().getId()) ?
			MemberResponse.from(chatRoom.getProduct().getMember()) : MemberResponse.from(chatRoom.getBuyer());
	}

	private ChatRoomLastMessageResponse getLastMessage(String uuid) {
		Message lastMessage = jpaQueryFactory
			.selectFrom(message)
			.where(
				message.chatRoomUuid.eq(uuid)
			)
			.orderBy(message.id.desc())
			.fetchFirst();

		return ChatRoomLastMessageResponse.from(lastMessage);
	}

	private Long getUnreadMessage(Long memberId, ChatRoom chatRoom) {
		return Objects.equals(memberId, chatRoom.getBuyer().getId()) ?
			unReadMessageCount(chatRoom.getUuid(), chatRoom.getBuyerLastReadMessageId()) :
			unReadMessageCount(chatRoom.getUuid(), chatRoom.getSellerLastReadMessageId());
	}

	private Long unReadMessageCount(String uuid, Long buyerLastReadMessageId) {
		return jpaQueryFactory
			.select(message.count())
			.from(message)
			.where(
				message.chatRoomUuid.eq(uuid),
				gtLastReadMessageId(buyerLastReadMessageId)
			)
			.fetchFirst();
	}

	private BooleanExpression gtLastReadMessageId(Long lastReadMessageId) {
		return lastReadMessageId == null ? null : message.id.gt(lastReadMessageId);
	}

	private BooleanExpression eqProductId(Long productId) {
		return productId == 0 ? null : chatRoom.product.id.eq(productId);
	}
}
