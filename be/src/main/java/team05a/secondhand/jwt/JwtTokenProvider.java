package team05a.secondhand.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final Key key;
	@Value("${jwt.token.access-expiration-time}")
	private long accessTokenExpirationTime;
	@Value("${jwt.token.refresh-expiration-time}")
	private long refreshTokenExpirationTime;

	public JwtTokenProvider(@Value("${jwt.token.secret}") String secretKey) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public Jwt createJwt(Map<String, Object> claims) {
		String accessToken = createAccessToken(claims);
		String refreshToken = createRefreshToken();
		return new Jwt(accessToken, refreshToken, refreshTokenExpirationTime);
	}

	public String createAccessToken(Map<String, Object> claims) {
		return createToken(claims, accessTokenExpirationTime);
	}

	public String createRefreshToken() {
		return createToken(new HashMap<>(), refreshTokenExpirationTime);
	}

	public String createToken(Map<String, Object> claims, long expireLength) {
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + expireLength);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expireDate)
			.signWith(key)
			.compact();
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Long extractMemberId(String accessToken) {
		return getClaims(accessToken).get("memberId", Long.class);
	}

	public Long getExpiration(String token) {
		Date expiration = getClaims(token).getExpiration();
		return expiration.getTime() - new Date().getTime();
	}

	public String getToken(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		return authorization.substring(7);
	}
}
