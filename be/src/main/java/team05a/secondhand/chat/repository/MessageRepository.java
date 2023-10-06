package team05a.secondhand.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.chat.data.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
}
