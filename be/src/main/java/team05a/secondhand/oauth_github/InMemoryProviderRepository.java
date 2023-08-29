package team05a.secondhand.oauth_github;

import java.util.HashMap;
import java.util.Map;

import team05a.secondhand.oauth_github.provider.OauthProvider;

public class InMemoryProviderRepository {

	private final Map<String, OauthProvider> providers;

	public InMemoryProviderRepository(Map<String, OauthProvider> providers) {
		this.providers = new HashMap<>(providers);
	}

	public OauthProvider findByProviderName(String name) {
		return providers.get(name);
	}
}
