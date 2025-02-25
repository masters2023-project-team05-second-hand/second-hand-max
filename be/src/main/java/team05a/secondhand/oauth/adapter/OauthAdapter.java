package team05a.secondhand.oauth.adapter;

import java.util.HashMap;
import java.util.Map;

import team05a.secondhand.oauth.properties.OauthProperties;
import team05a.secondhand.oauth.provider.OauthProvider;

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
