package team05a.secondhand.oauth_github.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import team05a.secondhand.oauth_github.InMemoryProviderRepository;
import team05a.secondhand.oauth_github.OauthAttributes;
import team05a.secondhand.oauth_github.data.dto.LoginResponse;
import team05a.secondhand.oauth_github.data.dto.MemberOauthRequest;
import team05a.secondhand.oauth_github.data.dto.OauthAccessCodeRequest;
import team05a.secondhand.oauth_github.data.dto.OauthTokenResponse;
import team05a.secondhand.oauth_github.provider.OauthProvider;
import team05a.secondhand.oauth_github.service.OauthService;

@RestController
public class OauthController {

	private final OauthService oauthService;
	private final InMemoryProviderRepository inMemoryProviderRepository;

	public OauthController(OauthService oauthService, InMemoryProviderRepository inMemoryProviderRepository) {
		this.oauthService = oauthService;
		this.inMemoryProviderRepository = inMemoryProviderRepository;
	}

	@PostMapping("/api/members/sign-in/{provider}")
	public ResponseEntity<LoginResponse> login(@PathVariable String provider,
		@RequestBody OauthAccessCodeRequest oauthAccessCodeRequest) {
		OauthProvider oauthProvider = inMemoryProviderRepository.findByProviderName(provider);
		OauthTokenResponse oauthTokenResponse = getToken(oauthAccessCodeRequest.getAccessCode(), oauthProvider);
		MemberOauthRequest memberOauthRequest = getMemberOauthRequest(provider, oauthTokenResponse, oauthProvider);
		LoginResponse loginResponse = oauthService.login(memberOauthRequest);
		return ResponseEntity.ok()
			.header(HttpHeaders.AUTHORIZATION, loginResponse.createAuthorizationHeader())
			.header(HttpHeaders.SET_COOKIE, loginResponse.getTokens().createRefreshToken())
			.body(loginResponse);
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
			.bodyValue(tokenRequest(code))
			.retrieve()
			.bodyToMono(OauthTokenResponse.class)
			.block();
	}

	private MultiValueMap<String, String> tokenRequest(String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
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
