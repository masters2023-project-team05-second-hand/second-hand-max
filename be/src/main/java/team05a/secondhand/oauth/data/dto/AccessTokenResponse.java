package team05a.secondhand.oauth.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccessTokenResponse {

	private final String accessToken;

	@Builder
	private AccessTokenResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public static AccessTokenResponse from(String accessToken) {
		return AccessTokenResponse.builder()
			.accessToken(accessToken)
			.build();
	}
}
