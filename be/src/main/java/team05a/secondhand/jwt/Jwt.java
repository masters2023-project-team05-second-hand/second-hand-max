package team05a.secondhand.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Jwt {

	private String accessToken;
	private String refreshToken;
	private long expirationTime;

	public Jwt(String accessToken, String refreshToken, long expirationTime) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expirationTime = expirationTime;
	}

	public String createAuthorizationHeader() {
		return "Bearer " + accessToken;
	}
}
