package team05a.secondhand.oauth_github.data.dto;

public class TokenResponse {

	private String accessToken;
	private String refreshToken;

	public TokenResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
