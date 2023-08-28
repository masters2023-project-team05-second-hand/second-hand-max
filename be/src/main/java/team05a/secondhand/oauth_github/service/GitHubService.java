package team05a.secondhand.oauth_github.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import team05a.secondhand.oauth_github.OauthAttributes;
import team05a.secondhand.oauth_github.data.dto.LoginResponse;
import team05a.secondhand.oauth_github.data.dto.MemberRequest;
import team05a.secondhand.oauth_github.data.dto.OauthTokenResponse;
import team05a.secondhand.oauth_github.data.entity.Member;
import team05a.secondhand.oauth_github.provider.GitHubOauthProvider;
import team05a.secondhand.oauth_github.repository.InMemoryProviderRepository;
import team05a.secondhand.oauth_github.repository.MemberRepository;

@Service
public class GitHubService {

	private final InMemoryProviderRepository inMemoryProviderRepository;
	private final MemberRepository memberRepository;

	public GitHubService(InMemoryProviderRepository inMemoryProviderRepository, MemberRepository memberRepository) {
		this.inMemoryProviderRepository = inMemoryProviderRepository;
		this.memberRepository = memberRepository;
	}

	public LoginResponse login(String provider, String code) {
		GitHubOauthProvider gitHubOauthProvider = inMemoryProviderRepository.findByProviderName(provider);
		OauthTokenResponse oauthTokenResponse = getToken(code, gitHubOauthProvider);
		MemberRequest memberRequest = getMemberRequest(provider, oauthTokenResponse, gitHubOauthProvider);
		Member member = save(memberRequest);
		String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
		String refreshToken = jwtTokenProvider.createRefreshToken();

		return new LoginResponse();
	}

	private OauthTokenResponse getToken(String code, GitHubOauthProvider provider) {
		return WebClient.create()
			.post()
			.uri(provider.getTokenUrl())
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

	private MultiValueMap<String, String> tokenRequest(String code, GitHubOauthProvider provider) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", provider.getRedirectUrl());
		return formData;
	}

	private MemberRequest getMemberRequest(String providerName, OauthTokenResponse tokenResponse, GitHubOauthProvider provider) {
		Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
		return OauthAttributes.extract(providerName, userAttributes);
	}

	private Map<String, Object> getUserAttributes(GitHubOauthProvider provider, OauthTokenResponse tokenResponse) {
		return WebClient.create()
			.get()
			.uri(provider.getUserInfoUrl())
			.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
			.block();
	}

	private Member save(MemberRequest memberRequest) {
		Member member = memberRepository.findByOauthId(memberRequest.getOauthId())
			.map(entity -> entity.update(
				userProfile.getEmail(), userProfile.getName(), userProfile.getImageUrl()))
			.orElseGet(userProfile::toMember);
		return memberRepository.save(member);
	}
}
