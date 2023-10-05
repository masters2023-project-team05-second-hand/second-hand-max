package team05a.secondhand.interceptor;

import static team05a.secondhand.oauth.service.OauthService.*;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

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

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

		try {
			String authorizationHeaders = String.valueOf(headerAccessor.getNativeHeader("Authorization").get(0));
			String accessToken = authorizationHeaders.substring(BEARER_PREFIX.length());

			if (redisRepository.get(ACCESS_TOKEN_PREFIX + accessToken).isPresent()) {
				throw new AccessTokenBlacklistException();
			}
			Long senderId = jwtTokenProvider.extractMemberId(accessToken);
			headerAccessor.addNativeHeader("senderId", String.valueOf(senderId));
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
		return message;
	}
}
