package team05a.secondhand.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import team05a.secondhand.redis.service.RedisSubscriber;

@Configuration
public class RedisConfig {

	private final String redisHost;
	private final int redisPort;

	public RedisConfig(@Value("${spring.redis.host}") String redisHost, @Value("${spring.redis.port}") int redisPort) {
		this.redisHost = redisHost;
		this.redisPort = redisPort;
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisHost, redisPort);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory((redisConnectionFactory()));
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisSubscriber redisSubscriber) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory());
		container.addMessageListener(listenerAdapter(redisSubscriber), channelTopic());
		return container;
	}

	@Bean
	public ChannelTopic channelTopic() {
		return new ChannelTopic("chatRoom");
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(RedisSubscriber redisSubscriber) {
		return new MessageListenerAdapter(redisSubscriber, "onMessage");
	}
}
