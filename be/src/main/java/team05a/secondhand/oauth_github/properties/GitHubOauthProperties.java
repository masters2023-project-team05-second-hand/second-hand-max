package team05a.secondhand.oauth_github.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "oauth2")
public class GitHubOauthProperties {

	private final Map<String, User> user = new HashMap<>();
	private final Map<String, Provider> provider = new HashMap<>();

	@Getter
	public static class User {
		private String clientId;
		private String clientSecret;
		private String redirectUri;
	}

	@Getter
	public static class Provider {
		private String tokenUri;
		private String userInfoUri;
		private String userNameAttribute;
	}
}
