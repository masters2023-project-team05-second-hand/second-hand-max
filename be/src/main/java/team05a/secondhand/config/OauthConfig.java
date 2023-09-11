package team05a.secondhand.config;

import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import team05a.secondhand.oauth.InMemoryProviderRepository;
import team05a.secondhand.oauth.adapter.OauthAdapter;
import team05a.secondhand.oauth.properties.OauthProperties;
import team05a.secondhand.oauth.provider.OauthProvider;

@Configuration
@EnableConfigurationProperties(OauthProperties.class)
public class OauthConfig {

	private final OauthProperties oauthProperties;

	public OauthConfig(OauthProperties oauthProperties) {
		this.oauthProperties = oauthProperties;
	}

	@Bean
	public InMemoryProviderRepository inMemoryProviderRepository() {
		Map<String, OauthProvider> providers = OauthAdapter.getOauthProviders(oauthProperties);
		return new InMemoryProviderRepository(providers);
	}
}
