package team05a.secondhand.websocket.chat_message.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.member.data.entity.Member;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chat_room_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private Member seller;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id")
	private Member buyer;
}
