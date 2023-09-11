package team05a.secondhand.address.integration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import team05a.secondhand.AcceptanceTest;

public class AddressTest extends AcceptanceTest {

	@DisplayName("동네 목록 조회를 한다.")
	@Test
	void findAllTest() {
		// given
		int page = 0;
		int size = 10;

		// when
		ExtractableResponse<Response> response = findAll(page, size);

		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softAssertions.assertThat(response.jsonPath().getString("addresses")).isNotEmpty();
			softAssertions.assertThat(response.jsonPath().getBoolean("hasNext")).isNotNull();
		});
	}

	private ExtractableResponse<Response> findAll(int page, int size) {
		return RestAssured
			.given().log().all()
			.queryParam("page", page)
			.queryParam("size", size)
			.when()
			.get("/api/addresses")
			.then().log().all()
			.extract();
	}
}
