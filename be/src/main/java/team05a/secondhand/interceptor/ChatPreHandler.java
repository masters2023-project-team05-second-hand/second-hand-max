package team05a.secondhand.interceptor;

import static team05a.secondhand.oauth.service.OauthService.*;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.errors.exception.AccessTokenBlacklistException;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.redis.repository.RedisRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatPreHandler implements ChannelInterceptor {

	private static final String BEARER_PREFIX = "Bearer ";

	private final JwtTokenProvider jwtTokenProvider;
	private final RedisRepository redisRepository;
	private final ObjectMapper objectMapper;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		Message<?> returnMessage = message;
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
		String accessToken = extractAccessToken(headerAccessor);

		validateAccessToken(accessToken);
		if (headerAccessor.getCommand().equals(StompCommand.SEND)) {
			try {
				Long senderId = jwtTokenProvider.extractMemberId(accessToken);
				String payload = (String)message.getPayload();
				ObjectNode jsonNode = objectMapper.readValue(payload, ObjectNode.class);
				jsonNode.put("senderId", senderId);
				String newPayload = objectMapper.writeValueAsString(jsonNode);
				MessageHeaders headers = message.getHeaders();
				returnMessage = MessageBuilder.createMessage(newPayload, headers);
			} catch (JsonMappingException e) {
				throw new MessageDeliveryException("json 매핑을 실패했습니다.");
			} catch (JsonProcessingException e) {
				throw new MessageDeliveryException("json 처리를 실패했습니다.");
			}
		}
		return returnMessage;
	}

	private void validateAccessToken(String accessToken) {
		try {
			if (redisRepository.get(ACCESS_TOKEN_PREFIX + accessToken).isPresent()) {
				throw new AccessTokenBlacklistException();
			}
			jwtTokenProvider.extractMemberId(accessToken);
		} catch (AccessTokenBlacklistException e) {
			log.warn("AccessTokenBlacklistException");
			throw new MessageDeliveryException("블랙리스트에 등록된 액세스 토큰으로는 접근할 수 없습니다.");
		} catch (MalformedJwtException | UnsupportedJwtException e) {
			log.error("JwtException");
			throw new MessageDeliveryException("인증 오류");
		} catch (ExpiredJwtException e) {
			log.error("JwtTokenExpired");
			throw new MessageDeliveryException("토큰이 만료 되었습니다");
		}
	}

	private String extractAccessToken(StompHeaderAccessor headerAccessor) {
		String authorizationHeaders = String.valueOf(headerAccessor.getNativeHeader("Authorization").get(0));
		return authorizationHeaders.substring(BEARER_PREFIX.length());
	}
}
