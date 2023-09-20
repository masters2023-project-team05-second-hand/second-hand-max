package team05a.secondhand.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.websocket.chat_room.data.dto.ChatMessageRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisPublisher {

	private final RedisTemplate<String, Object> redisTemplate;

	public void publish(ChannelTopic channelTopic, ChatMessageRequest chatMessageRequest) {
		log.info("Topic : " + channelTopic.getTopic() + ", Message : " + chatMessageRequest.getMessage());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
		redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessageRequest);
	}
}
