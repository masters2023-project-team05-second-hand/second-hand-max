package team05a.secondhand.oauth.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@ConfigurationProperties(prefix = "oauth2")
public class OauthProperties {

	private final Map<String, Member> member = new HashMap<>();
	private final Map<String, Provider> provider = new HashMap<>();

	@Getter
	@Setter
	public static class Member {
		private String clientId;
		private String clientSecret;
		private String redirectUri;
	}

	@Getter
	@Setter
	public static class Provider {
		private String tokenUri;
		private String memberInfoUri;
		private String memberNameAttribute;
	}
}
