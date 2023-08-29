package team05a.secondhand.oauth_github.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import team05a.secondhand.oauth_github.data.dto.LoginResponse;
import team05a.secondhand.oauth_github.service.OauthService;

@RestController
public class OauthController {

	private OauthService oauthService;

	public OauthController(OauthService oauthService) {
		this.oauthService = oauthService;
	}

	@PostMapping("/api/members/sign-in/{provider}")
	public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestBody String accessCode) {
		LoginResponse loginResponse = oauthService.login(provider, accessCode);
		return ResponseEntity.ok().body(loginResponse);
	}
}
