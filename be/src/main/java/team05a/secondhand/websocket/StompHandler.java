package team05a.secondhand.websocket;

import static team05a.secondhand.oauth.service.OauthService.*;

import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.errors.exception.AccessTokenBlacklistException;
import team05a.secondhand.errors.exception.AuthenticationHeaderNotFoundException;
import team05a.secondhand.redis.repository.RedisRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

	private final RedisRepository redisRepository;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);
		String sessionId = stompHeaderAccessor.getSessionId();

		try {
			String accessToken = getToken(stompHeaderAccessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION));
			if (redisRepository.get(ACCESS_TOKEN_PREFIX + accessToken).isPresent()) {
				throw new AccessTokenBlacklistException();
			}
		} catch (MalformedJwtException | UnsupportedJwtException e) {
			log.error("JwtException");
		} catch (ExpiredJwtException e) {
			log.error("JwtTokenExpired");
		}

		switch (Objects.requireNonNull(stompHeaderAccessor.getCommand())) {
			case CONNECT:
				log.info("CONNECT: {}", sessionId);
				break;
			case DISCONNECT:
				log.info("DISCONNECT: {}", sessionId);
				break;
		}

		return message;
	}

	private String getToken(String authorization) {
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			throw new AuthenticationHeaderNotFoundException();
		}
		return authorization.substring(7);
	}
}
