package team05a.secondhand.oauth_github.provider;

import lombok.Getter;
import team05a.secondhand.oauth_github.properties.GitHubOauthProperties;

@Getter
public class GitHubOauthProvider {

	private final String clientId;
	private final String clientSecret;
	private final String redirectUrl;
	private final String tokenUrl;
	private final String userInfoUrl;

	public GitHubOauthProvider(GitHubOauthProperties.User user, GitHubOauthProperties.Provider provider) {
		this(user.getClientId(), user.getClientSecret(), user.getRedirectUri(), provider.getTokenUri(),
			provider.getUserInfoUri());
	}

	public GitHubOauthProvider(String clientId, String clientSecret, String redirectUrl, String tokenUrl,
		String userInfoUrl) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUrl = redirectUrl;
		this.tokenUrl = tokenUrl;
		this.userInfoUrl = userInfoUrl;
	}
}
