package team05a.secondhand.oauth_github.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class OauthTokenResponse {

	@JsonProperty("access_token")
	private String accessToken;
	private String scope;
	@JsonProperty("token_type")
	private String tokenType;

	public OauthTokenResponse() {
	}

	public OauthTokenResponse(String accessToken, String scope, String tokenType) {
		this.accessToken = accessToken;
		this.scope = scope;
		this.tokenType = tokenType;
	}
}
