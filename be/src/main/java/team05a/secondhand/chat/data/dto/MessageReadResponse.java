package team05a.secondhand.chat.data.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.chat.data.entity.Message;

@NoArgsConstructor
@Getter
public class MessageReadResponse {

	private Long senderId;
	private String content;
	private Timestamp sentTime;
	private Boolean isRead;

	@Builder
	private MessageReadResponse(Long senderId, String content, Timestamp sentTime, Boolean isRead) {
		this.senderId = senderId;
		this.content = content;
		this.sentTime = sentTime;
		this.isRead = isRead;
	}

	public static MessageReadResponse from(Message message, Long readMessageId) {
		return MessageReadResponse.builder()
			.senderId(message.getSender().getId())
			.content(message.getContent())
			.sentTime(message.getCreatedTime())
			.isRead(message.getId() <= readMessageId)
			.build();
	}
}
