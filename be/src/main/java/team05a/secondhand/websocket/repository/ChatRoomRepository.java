package team05a.secondhand.websocket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.websocket.chat_message.data.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
