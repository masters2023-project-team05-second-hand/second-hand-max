package team05a.secondhand.redis.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RedisRepository {

	private final RedisTemplate<String, Object> redisTemplate;

	public void setTime(String key, Object o, Long time) {
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
		redisTemplate.opsForValue().set(key, o, time, TimeUnit.MILLISECONDS);
	}

	public void set(String key, Object o) {
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
		redisTemplate.opsForValue().set(key, o);
	}

	public Optional<Object> get(String key) {
		return Optional.ofNullable(redisTemplate.opsForValue().get(key));
	}

	public boolean delete(String key) {
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}
}
