package team05a.secondhand.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Jwt {

	private String accessToken;
	private String refreshToken;
	@JsonIgnore
	private long refreshTokenExpirationTime;

	public Jwt(String accessToken, String refreshToken, long refreshTokenExpirationTime) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.refreshTokenExpirationTime = refreshTokenExpirationTime;
	}

	public String createRefreshToken() {
		return "refreshToken="
			+ refreshToken
			+ "; Max-Age="
			+ refreshTokenExpirationTime
			+ "; HttpOnly";
	}
}
