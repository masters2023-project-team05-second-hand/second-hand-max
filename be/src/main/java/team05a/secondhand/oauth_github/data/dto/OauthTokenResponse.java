package team05a.secondhand.oauth_github.data.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OauthTokenResponse {

	private String accessToken;
	private String scope;
	private String tokenType;

	public OauthTokenResponse() {
	}
}
