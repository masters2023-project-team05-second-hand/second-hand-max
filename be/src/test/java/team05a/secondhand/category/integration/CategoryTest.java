package team05a.secondhand.category.integration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import team05a.secondhand.AcceptanceTest;

public class CategoryTest extends AcceptanceTest {

	@DisplayName("카테고리 목록 조회를 한다.")
	@Test
	void findAllTest() {
		// given & when
		var response = findAll();

		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softAssertions.assertThat(response.jsonPath().getString("categories.id")).isNotEmpty();
		});
	}

	private ExtractableResponse<Response> findAll() {
		return RestAssured
			.given().log().all()
			.when()
			.get("/api/categories")
			.then().log().all()
			.extract();
	}
}
