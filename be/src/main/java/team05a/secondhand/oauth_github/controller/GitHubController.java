package team05a.secondhand.oauth_github.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import team05a.secondhand.oauth_github.data.dto.LoginResponse;
import team05a.secondhand.oauth_github.service.GitHubService;

@RestController
public class GitHubController {

	private GitHubService gitHubService;

	public GitHubController(GitHubService gitHubService) {
		this.gitHubService = gitHubService;
	}

	@PostMapping("/login/oauth/{provider}")
	public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestParam String code) {
		LoginResponse loginResponse = gitHubService.login(provider, code);
		return ResponseEntity.ok().body(loginResponse);
	}
}
