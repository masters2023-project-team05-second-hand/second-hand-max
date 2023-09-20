package team05a.secondhand.websocket.chat_room.data.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.websocket.chat_message.data.entity.ChatRoom;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chat_message_id")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_room_id")
	private ChatRoom chatRoom;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private Member sender;
	private String message;
	@CreationTimestamp
	private Timestamp createdTime;
}
