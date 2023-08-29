package team05a.secondhand.oauth_github.provider;

import lombok.Getter;
import team05a.secondhand.oauth_github.properties.OauthProperties;

@Getter
public class OauthProvider {

	private final String clientId;
	private final String clientSecret;
	private final String redirectUri;
	private final String tokenUri;
	private final String memberInfoUri;

	public OauthProvider(OauthProperties.Member member, OauthProperties.Provider provider) {
		this(member.getClientId(), member.getClientSecret(), member.getRedirectUri(), provider.getTokenUri(),
			provider.getMemberInfoUri());
	}

	public OauthProvider(String clientId, String clientSecret, String redirectUri, String tokenUri,
		String memberInfoUri) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
		this.tokenUri = tokenUri;
		this.memberInfoUri = memberInfoUri;
	}
}
