package team05a.secondhand.oauth.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import team05a.secondhand.AcceptanceTest;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.jwt.Jwt;
import team05a.secondhand.oauth.data.dto.OauthRefreshTokenRequest;
import team05a.secondhand.oauth.service.OauthService;

public class OauthTest extends AcceptanceTest {

	@Autowired
	private OauthService oauthService;

	@DisplayName("로그아웃한다.")
	@Test
	void createProduct_success() {
		// given
		Jwt jwt = login();
		String accessToken = jwt.getAccessToken();
		OauthRefreshTokenRequest oauthRefreshTokenRequest = OauthRefreshTokenRequest.builder()
			.refreshToken(jwt.getRefreshToken())
			.build();

		// when
		var response = logout(accessToken, oauthRefreshTokenRequest);

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	private ExtractableResponse<Response> logout(String accessToken,
		OauthRefreshTokenRequest oauthRefreshTokenRequest) {
		return RestAssured
			.given().log().all()
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
			.body(oauthRefreshTokenRequest)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.post("/api/sign-out")
			.then().log().all()
			.extract();
	}

	private Jwt login() {
		return oauthService.login(FixtureFactory.createMemberRequest());
	}
}
