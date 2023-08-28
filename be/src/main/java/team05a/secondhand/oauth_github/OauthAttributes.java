package team05a.secondhand.oauth_github;

import java.util.Arrays;
import java.util.Map;

import team05a.secondhand.oauth_github.data.dto.MemberRequest;

public enum OauthAttributes {

	GITHUB("github") {
		@Override
		public MemberRequest of(Map<String, Object> attributes) {
			return new MemberRequest((String) attributes.get("nickname"), (String) attributes.get("email"), (String) attributes.get("profile_url"));
		}
	};

	private final String providerName;

	OauthAttributes(String name) {
		this.providerName = name;
	}

	public static MemberRequest extract(String providerName, Map<String, Object> attributes) {
		return Arrays.stream(values())
			.filter(provider -> providerName.equals(provider.providerName))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new)
			.of(attributes);
	}

	public abstract MemberRequest of(Map<String, Object> attributes);
}
