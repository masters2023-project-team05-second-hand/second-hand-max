package team05a.secondhand.oauth_github.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.member_address.data.entity.MemberAddress;
import team05a.secondhand.member_address.repository.MemberAddressRepository;
import team05a.secondhand.oauth_github.InMemoryProviderRepository;
import team05a.secondhand.oauth_github.OauthAttributes;
import team05a.secondhand.oauth_github.data.dto.AddressResponse;
import team05a.secondhand.oauth_github.data.dto.LoginResponse;
import team05a.secondhand.oauth_github.data.dto.MemberOauthRequest;
import team05a.secondhand.oauth_github.data.dto.OauthTokenResponse;
import team05a.secondhand.oauth_github.data.dto.TokenResponse;
import team05a.secondhand.oauth_github.provider.OauthProvider;

@Service
public class OauthService {

	private final InMemoryProviderRepository inMemoryProviderRepository;
	private final MemberRepository memberRepository;
	private final MemberAddressRepository memberAddressRepository;
	private final JwtTokenProvider jwtTokenProvider;

	public OauthService(InMemoryProviderRepository inMemoryProviderRepository, MemberRepository memberRepository,
		MemberAddressRepository memberAddressRepository, JwtTokenProvider jwtTokenProvider) {
		this.inMemoryProviderRepository = inMemoryProviderRepository;
		this.memberRepository = memberRepository;
		this.memberAddressRepository = memberAddressRepository;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public LoginResponse login(String providerName, String code) {
		OauthProvider oauthProvider = inMemoryProviderRepository.findByProviderName(providerName);
		OauthTokenResponse oauthTokenResponse = getToken(code, oauthProvider);
		MemberOauthRequest memberOauthRequest = getMemberOauthRequest(providerName, oauthTokenResponse, oauthProvider);
		Member member = save(memberOauthRequest);
		List<AddressResponse> address = memberAddressRepository.findByMemberId(member.getId());
		String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
		String refreshToken = jwtTokenProvider.createRefreshToken();

		return new LoginResponse(new TokenResponse(accessToken, refreshToken), address);
	}

	private OauthTokenResponse getToken(String code, OauthProvider provider) {
		return WebClient.create()
			.post()
			.uri(provider.getTokenUri())
			.headers(header -> {
				header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.bodyValue(tokenRequest(code, provider))
			.retrieve()
			.bodyToMono(OauthTokenResponse.class)
			.block();
	}

	private MultiValueMap<String, String> tokenRequest(String code, OauthProvider provider) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", provider.getRedirectUri());
		return formData;
	}

	private MemberOauthRequest getMemberOauthRequest(String providerName, OauthTokenResponse tokenResponse,
		OauthProvider provider) {
		Map<String, Object> memberAttributes = getMemberAttributes(provider, tokenResponse);
		return OauthAttributes.extract(providerName, memberAttributes);
	}

	private Map<String, Object> getMemberAttributes(OauthProvider provider, OauthTokenResponse tokenResponse) {
		return WebClient.create()
			.get()
			.uri(provider.getMemberInfoUri())
			.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

	private Member save(MemberOauthRequest memberOauthRequest) {
		return memberRepository.findByEmailAndType(memberOauthRequest.getEmail(), memberOauthRequest.getType())
			.orElseGet(() -> {
				Member member = memberRepository.save(Member.from(memberOauthRequest));
				memberAddressRepository.save(MemberAddress.from(member.getId(), null));
				return member;
			});
	}
}
