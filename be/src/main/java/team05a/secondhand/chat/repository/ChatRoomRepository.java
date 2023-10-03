package team05a.secondhand.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.chat.data.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
