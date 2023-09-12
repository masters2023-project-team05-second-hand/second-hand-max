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
import team05a.secondhand.member_refreshtoken.repository.MemberRefreshTokenRepository;
import team05a.secondhand.oauth.data.dto.MemberOauthRequest;
import team05a.secondhand.oauth.data.dto.OauthRefreshTokenRequest;

@Service
@RequiredArgsConstructor
public class OauthService {

	public static final String REFRESH_TOKEN_PREFIX = "refreshToken:";
	public static final String ACCESS_TOKEN_PREFIX = "accessToken:";

	private final MemberRepository memberRepository;
	private final MemberRefreshTokenRepository memberRefreshTokenRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public Jwt login(MemberOauthRequest memberOauthRequest) {
		Member member = save(memberOauthRequest);

		Jwt jwt = jwtTokenProvider.createJwt(Map.of("memberId", member.getId()));
		String refreshToken = jwt.getRefreshToken();
		memberRefreshTokenRepository.set(REFRESH_TOKEN_PREFIX + refreshToken, member.getId(),
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
		memberRefreshTokenRepository.set(ACCESS_TOKEN_PREFIX + accessToken, memberId,
			jwtTokenProvider.getExpiration(accessToken));
		memberRefreshTokenRepository.delete(refreshToken);
	}

	private void validateRedisMemberId(String refreshToken, Long memberId) {
		Long redisMemberId = Long.parseLong(String.valueOf(
			memberRefreshTokenRepository.get(refreshToken).orElseThrow(RefreshTokenNotFoundException::new)));
		if (!redisMemberId.equals(memberId)) {
			throw new TokenMemberMismatchException();
		}
	}
}
