package team05a.secondhand.chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.chat.data.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {

	boolean existsByBuyerIdAndProductId(Long buyerId, Long productId);

	Optional<ChatRoom> findByBuyerIdAndProductId(Long buyerId, Long productId);
}
