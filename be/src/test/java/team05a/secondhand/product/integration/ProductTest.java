package team05a.secondhand.product.integration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
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
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.repository.MemberRepository;

public class ProductTest extends AcceptanceTest {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("상품을 등록한다.")
	@Test
	void createTest() throws IOException {
		// given
		Long memberId = singUp();

		// when
		var response = create(memberId);

		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softAssertions.assertThat(response.jsonPath().getLong("productId")).isNotNull();
		});
	}

	private Long singUp() {
		return memberRepository.save(FixtureFactory.createMember()).getId();
	}

	private ExtractableResponse<Response> create(Long memberId) throws IOException {
		return RestAssured
			.given().log().all()
			.multiPart("images", File.createTempFile("test", "jpeg"), MediaType.IMAGE_JPEG_VALUE)
			.multiPart("title", "title")
			.multiPart("content", "content")
			.multiPart("categoryId", 1)
			.multiPart("addressId", 1)
			.multiPart("price", "null")
			.header(HttpHeaders.AUTHORIZATION,
				"Bearer " + jwtTokenProvider.createAccessToken(Map.of("memberId", memberId)))
			.when()
			.post("/api/products")
			.then().log().all()
			.extract();
	}
}
