package team05a.secondhand.oauth.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.errors.exception.RefreshTokenNotFoundException;
import team05a.secondhand.errors.exception.TokenMemberMismatchException;
import team05a.secondhand.jwt.Jwt;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.oauth.data.dto.MemberOauthRequest;
import team05a.secondhand.oauth.data.dto.OauthRefreshTokenRequest;
import team05a.secondhand.redis.repository.RedisRepository;

@Service
@RequiredArgsConstructor
public class OauthService {

	public static final String REFRESH_TOKEN_PREFIX = "refreshToken:";
	public static final String ACCESS_TOKEN_PREFIX = "accessToken:";

	private final MemberRepository memberRepository;
	private final RedisRepository redisRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public Jwt login(MemberOauthRequest memberOauthRequest) {
		Member member = save(memberOauthRequest);

		Jwt jwt = jwtTokenProvider.createJwt(Map.of("memberId", member.getId()));
		String refreshToken = jwt.getRefreshToken();
		redisRepository.setTime(REFRESH_TOKEN_PREFIX + refreshToken, member.getId(),
			jwtTokenProvider.getExpiration(refreshToken));

		return jwt;
	}

	private Member save(MemberOauthRequest memberOauthRequest) {
		return memberRepository.findByEmailAndType(memberOauthRequest.getEmail(), memberOauthRequest.getType())
			.orElseGet(() -> memberRepository.save(Member.from(memberOauthRequest)));
	}

	@Transactional
	public void logout(OauthRefreshTokenRequest oauthRefreshTokenRequest, String accessToken, Long memberId) {
		String refreshToken = REFRESH_TOKEN_PREFIX + oauthRefreshTokenRequest.getRefreshToken();
		validateRedisMemberId(refreshToken, memberId);
		redisRepository.setTime(ACCESS_TOKEN_PREFIX + accessToken, memberId,
			jwtTokenProvider.getExpiration(accessToken));
		redisRepository.delete(refreshToken);
	}

	private void validateRedisMemberId(String refreshToken, Long memberId) {
		Long redisMemberId = getMemberIdBy(refreshToken);
		if (!redisMemberId.equals(memberId)) {
			throw new TokenMemberMismatchException();
		}
	}

	public String reissueAccessToken(OauthRefreshTokenRequest oauthRefreshTokenRequest) {
		String refreshToken = REFRESH_TOKEN_PREFIX + oauthRefreshTokenRequest.getRefreshToken();
		Long memberId = getMemberIdBy(refreshToken);
		return jwtTokenProvider.createAccessToken(Map.of("memberId", memberId));
	}

	private Long getMemberIdBy(String refreshToken) {
		return (Long)redisRepository.get(refreshToken).orElseThrow(RefreshTokenNotFoundException::new);
	}
}
