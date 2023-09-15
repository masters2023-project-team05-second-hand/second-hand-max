package team05a.secondhand.oauth.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthRefreshTokenRequest {

	private String refreshToken;

	@Builder
	private OauthRefreshTokenRequest(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
