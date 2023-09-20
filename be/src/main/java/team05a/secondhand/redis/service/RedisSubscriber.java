package team05a.secondhand.redis.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.websocket.chat_room.data.dto.ChatMessageRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;
	private final SimpMessageSendingOperations messageTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			log.info("Redis Subscriber 호출");
			String publishMessage = redisTemplate.getStringSerializer()
				.deserialize(message.getBody());
			log.info("Publish Message : " + publishMessage);
			ChatMessageRequest chatMessageRequest = objectMapper.readValue(publishMessage, ChatMessageRequest.class);
			log.info("Redis Subscribe Message : " + chatMessageRequest.getMessage());
			messageTemplate.convertAndSend("/sub/chat/room/" + chatMessageRequest.getChatRoomId(),
				chatMessageRequest.getMessage());
		} catch (JsonProcessingException e) {
			//TODO 에러 던지기
			log.error(e.getMessage());
		}
	}
}
