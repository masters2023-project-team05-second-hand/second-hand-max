package team05a.secondhand.oauth.controller;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.jwt.Jwt;
import team05a.secondhand.jwt.resolver.AccessToken;
import team05a.secondhand.member.resolver.MemberId;
import team05a.secondhand.oauth.InMemoryProviderRepository;
import team05a.secondhand.oauth.OauthAttributes;
import team05a.secondhand.oauth.data.dto.AccessTokenResponse;
import team05a.secondhand.oauth.data.dto.MemberOauthRequest;
import team05a.secondhand.oauth.data.dto.OauthAccessCode;
import team05a.secondhand.oauth.data.dto.OauthRefreshTokenRequest;
import team05a.secondhand.oauth.data.dto.OauthTokenResponse;
import team05a.secondhand.oauth.provider.OauthProvider;
import team05a.secondhand.oauth.service.OauthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OauthController {

	private final OauthService oauthService;
	private final InMemoryProviderRepository inMemoryProviderRepository;

	@PostMapping("/members/sign-in/{provider}")
	public ResponseEntity<Jwt> login(@PathVariable String provider,
		@RequestBody OauthAccessCode oauthAccessCode) {
		OauthProvider oauthProvider = inMemoryProviderRepository.findByProviderName(provider);
		OauthTokenResponse oauthTokenResponse = getToken(oauthAccessCode.getAccessCode(), oauthProvider);
		MemberOauthRequest memberOauthRequest = getMemberOauthRequest(provider, oauthTokenResponse, oauthProvider);
		Jwt jwt = oauthService.login(memberOauthRequest);

		return ResponseEntity.ok()
			.header(HttpHeaders.AUTHORIZATION, jwt.createAuthorizationHeader())
			.body(jwt);
	}

	@PostMapping("/sign-out")
	public ResponseEntity<Void> logout(@RequestBody OauthRefreshTokenRequest oauthRefreshTokenRequest,
		@AccessToken String accessToken, @MemberId Long memberId) {
		oauthService.logout(oauthRefreshTokenRequest, accessToken, memberId);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/reissue-access-token")
	public ResponseEntity<AccessTokenResponse> reissueAccessToken(
		@RequestBody OauthRefreshTokenRequest oauthRefreshTokenRequest) {
		String accessToken = oauthService.reissueAccessToken(oauthRefreshTokenRequest);
		String authorization = "Bearer " + accessToken;

		return ResponseEntity.ok()
			.header(HttpHeaders.AUTHORIZATION, authorization)
			.body(AccessTokenResponse.from(accessToken));
	}

	private OauthTokenResponse getToken(String code, OauthProvider provider) {
		return WebClient.create()
			.post()
			.uri(provider.getTokenUri())
			.headers(header -> {
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.bodyValue(tokenRequest(provider, code))
			.retrieve()
			.bodyToMono(OauthTokenResponse.class)
			.block();
	}

	private MultiValueMap<String, String> tokenRequest(OauthProvider provider, String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("client_id", provider.getClientId());
		formData.add("client_secret", provider.getClientSecret());
		formData.add("redirect_url", provider.getRedirectUri());
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		return formData;
	}

	private MemberOauthRequest getMemberOauthRequest(String providerName, OauthTokenResponse tokenResponse,
		OauthProvider provider) {
		Map<String, Object> memberAttributes = getMemberAttributes(provider, tokenResponse);
		if (providerName.equals(OauthAttributes.GITHUB.getProviderName()) && memberAttributes.get("email") == null) {
			memberAttributes.put("email", getMemberEmail(tokenResponse).get(0).get("email"));
		}
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

	public List<Map<String, Object>> getMemberEmail(OauthTokenResponse tokenResponse) {
		return WebClient.create()
			.get()
			.uri("https://api.github.com/user/emails")
			.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
			})
			.block();
	}
}
