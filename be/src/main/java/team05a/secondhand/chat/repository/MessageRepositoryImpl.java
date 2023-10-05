package team05a.secondhand.chat.repository;

import static team05a.secondhand.chat.data.entity.QChatRoom.*;
import static team05a.secondhand.chat.data.entity.QMessage.*;

import java.util.List;
import java.util.stream.Collectors;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.chat.data.dto.MessageReadResponse;
import team05a.secondhand.chat.data.entity.ChatRoom;
import team05a.secondhand.chat.data.entity.Message;
import team05a.secondhand.errors.exception.IllegalChatRoomParticipantException;

@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<MessageReadResponse> findChatMessages(Long memberId, String roomId) {
		List<Message> messageList = jpaQueryFactory
			.selectFrom(message)
			.where(
				message.chatRoomUuid.eq(roomId)
			)
			.fetch();
		ChatRoom room = jpaQueryFactory
			.selectFrom(chatRoom)
			.where(
				chatRoom.uuid.eq(roomId)
			)
			.fetchFirst();
		Long readMessageId = getReadMessageId(memberId, room, messageList.get(messageList.size() - 1).getId());

		return messageList.stream()
			.map(m -> MessageReadResponse.from(m, readMessageId))
			.collect(Collectors.toUnmodifiableList());
	}

	private Long getReadMessageId(Long memberId, ChatRoom room, Long messageId) {
		if (memberId.equals(room.getBuyer().getId())) {
			jpaQueryFactory.update(chatRoom)
				.set(chatRoom.buyerLastReadMessageId, messageId)
				.where(chatRoom.id.eq(room.getId()))
				.execute();
			return room.getBuyerLastReadMessageId() == null ? 0L : room.getBuyerLastReadMessageId();
		}
		if (memberId.equals(room.getProduct().getMember().getId())) {
			jpaQueryFactory.update(chatRoom)
				.set(chatRoom.sellerLastReadMessageId, messageId)
				.where(chatRoom.id.eq(room.getId()))
				.execute();
			return room.getSellerLastReadMessageId() == null ? 0L : room.getSellerLastReadMessageId();
		}
		throw new IllegalChatRoomParticipantException();
	}
}
