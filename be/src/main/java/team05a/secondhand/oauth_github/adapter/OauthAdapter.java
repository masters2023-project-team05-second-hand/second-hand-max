package team05a.secondhand.oauth_github.adapter;

import java.util.HashMap;
import java.util.Map;

import team05a.secondhand.oauth_github.properties.OauthProperties;
import team05a.secondhand.oauth_github.provider.OauthProvider;

public class OauthAdapter {

	private OauthAdapter() {
	}

	public static Map<String, OauthProvider> getOauthProviders(OauthProperties properties) {
		Map<String, OauthProvider> oauthProvider = new HashMap<>();
		properties.getMember().forEach((key, value) -> oauthProvider.put(key,
			new OauthProvider(value, properties.getProvider().get(key))));
		return oauthProvider;
	}
}
