package team05a.secondhand.oauth_github;

import java.util.Arrays;
import java.util.Map;

import team05a.secondhand.oauth_github.data.dto.MemberOauthRequest;

public enum OauthAttributes {

	GITHUB("github") {
		@Override
		public MemberOauthRequest of(String providerName, Map<String, Object> attributes) {
			return new MemberOauthRequest((String)attributes.get("login"), (String)attributes.get("email"),
				(String)attributes.get("avatar_url"),
				providerName);
		}
	};

	private final String providerName;

	OauthAttributes(String name) {
		this.providerName = name;
	}

	public static MemberOauthRequest extract(String providerName, Map<String, Object> attributes) {
		return Arrays.stream(values())
			.filter(provider -> providerName.equals(provider.providerName))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new)
			.of(providerName, attributes);
	}

	public abstract MemberOauthRequest of(String providerName, Map<String, Object> attributes);
}
