package team05a.secondhand.oauth_github.repository;

import java.util.HashMap;
import java.util.Map;

import team05a.secondhand.oauth_github.provider.GitHubOauthProvider;

public class InMemoryProviderRepository {

	private final Map<String, GitHubOauthProvider> providers;

	public InMemoryProviderRepository(Map<String, GitHubOauthProvider> providers) {
		this.providers = new HashMap<>(providers);
	}

	public GitHubOauthProvider findByProviderName(String name) {
		return providers.get(name);
	}
}
