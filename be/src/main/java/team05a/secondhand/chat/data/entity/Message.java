package team05a.secondhand.chat.data.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.member.data.entity.Member;

@Entity
@NoArgsConstructor
@Getter
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String chatRoomUuid;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member sender;
	@Lob
	private String content;
	@CreationTimestamp
	private Timestamp createdTime;

	@Builder
	private Message(Long id, String chatRoomUuid, Member sender, String content, Timestamp createdTime) {
		this.id = id;
		this.chatRoomUuid = chatRoomUuid;
		this.sender = sender;
		this.content = content;
		this.createdTime = createdTime;
	}
}
