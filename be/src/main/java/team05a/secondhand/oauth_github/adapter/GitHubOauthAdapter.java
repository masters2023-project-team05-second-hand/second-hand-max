package team05a.secondhand.oauth_github.adapter;

import java.util.HashMap;
import java.util.Map;

import team05a.secondhand.oauth_github.properties.GitHubOauthProperties;
import team05a.secondhand.oauth_github.provider.GitHubOauthProvider;

public class GitHubOauthAdapter {

	private GitHubOauthAdapter() {}

	public static Map<String, GitHubOauthProvider> getGitHubOauthProviders(GitHubOauthProperties properties) {
		Map<String, GitHubOauthProvider> oauthProvider = new HashMap<>();

		properties.getUser().forEach((key, value) -> oauthProvider.put(key,
			new GitHubOauthProvider(value, properties.getProvider().get(key))));
		return oauthProvider;
	}
}
