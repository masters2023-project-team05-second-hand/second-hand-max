package team05a.secondhand.oauth_github.config;

import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import team05a.secondhand.oauth_github.adapter.GitHubOauthAdapter;
import team05a.secondhand.oauth_github.properties.GitHubOauthProperties;
import team05a.secondhand.oauth_github.provider.GitHubOauthProvider;
import team05a.secondhand.oauth_github.repository.InMemoryProviderRepository;

@Configuration
@EnableConfigurationProperties(GitHubOauthProperties.class)
public class GitHubOauthConfig {

	private final GitHubOauthProperties gitHubOauthProperties;

	public GitHubOauthConfig(GitHubOauthProperties gitHubOauthProperties) {
		this.gitHubOauthProperties = gitHubOauthProperties;
	}

	@Bean
	public InMemoryProviderRepository inMemoryProviderRepository() {
		Map<String, GitHubOauthProvider> providers = GitHubOauthAdapter.getGitHubOauthProviders(gitHubOauthProperties);
		return new InMemoryProviderRepository(providers);
	}
}
