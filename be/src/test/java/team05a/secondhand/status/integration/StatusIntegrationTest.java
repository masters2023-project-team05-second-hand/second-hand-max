package team05a.secondhand.status.integration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StatusIntegrationTest {

	@DisplayName("상태 목록 조회를 한다.")
	@Test
	void findAll() {
		// given & when
		ExtractableResponse<Response> response = RestAssured
			.given().log().all()
			.when()
			.get("/api/statuses")
			.then().log().all()
			.extract();

		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softAssertions.assertThat(response.jsonPath().getString("id")).isNotEmpty();
		});
	}
}
