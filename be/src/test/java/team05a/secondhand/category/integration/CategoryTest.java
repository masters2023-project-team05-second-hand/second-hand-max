package team05a.secondhand.category.integration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CategoryTest {

	@DisplayName("카테고리 목록 조회를 한다.")
	@Test
	void findAllTest() {
		// given & when
		var response = findAll();

		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softAssertions.assertThat(response.jsonPath().getString("id")).isNotEmpty();
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
