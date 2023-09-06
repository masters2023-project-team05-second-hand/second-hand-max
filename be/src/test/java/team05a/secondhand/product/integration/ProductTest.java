package team05a.secondhand.product.integration;

import static org.assertj.core.api.Assertions.*;

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
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;

public class ProductTest extends AcceptanceTest {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ProductRepository productRepository;

	@DisplayName("상품을 등록한다.")
	@Test
	void createProduct_success() throws IOException {
		// given
		Member member = singUp();

		// when
		var response = create(member.getId());

		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softAssertions.assertThat(response.jsonPath().getLong("productId")).isNotNull();
		});
	}

	private ExtractableResponse<Response> create(Long memberId) throws IOException {
		return RestAssured
			.given().log().all()
			.multiPart("images", File.createTempFile("create", "jpeg"), MediaType.IMAGE_JPEG_VALUE)
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

	@DisplayName("상품을 수정한다.")
	@Test
	void updateProduct_success() throws IOException {
		// given
		Member member = singUp();
		Product product = createProduct(member);

		// when
		var response = update(product, member);

		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softAssertions.assertThat(response.jsonPath().getLong("productId")).isNotNull();
		});
	}

	private ExtractableResponse<Response> update(Product product, Member member) throws IOException {
		return RestAssured
			.given().log().all()
			.pathParam("productId", product.getId())
			.multiPart("newImages", File.createTempFile("update", "jpeg"), MediaType.IMAGE_JPEG_VALUE)
			.multiPart("title", "title update")
			.multiPart("content", "content update")
			.multiPart("categoryId", 1)
			.multiPart("addressId", 1)
			.multiPart("price", "1000")
			.multiPart("deletedImageIds", "")
			.header(HttpHeaders.AUTHORIZATION,
				"Bearer " + jwtTokenProvider.createAccessToken(Map.of("memberId", member.getId())))
			.when()
			.patch("/api/products/{productId}")
			.then().log().all()
			.extract();
	}

	@DisplayName("상품 제목이 없을 경우 400 에러를 반환한다.")
	@Test
	void updateProduct_fail_NoTitle() throws IOException {
		// given
		Member member = singUp();
		Product product = createProduct(member);

		// when
		var response = updateNoTitle(product, member);

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	private ExtractableResponse<Response> updateNoTitle(Product product, Member member) throws IOException {
		return RestAssured
			.given().log().all()
			.pathParam("productId", product.getId())
			.multiPart("newImages", File.createTempFile("update", "jpeg"), MediaType.IMAGE_JPEG_VALUE)
			.multiPart("content", "content update")
			.multiPart("categoryId", 1)
			.multiPart("addressId", 1)
			.multiPart("price", "1000")
			.multiPart("deletedImageIds", "")
			.header(HttpHeaders.AUTHORIZATION,
				"Bearer " + jwtTokenProvider.createAccessToken(Map.of("memberId", member.getId())))
			.when()
			.patch("/api/products/{productId}")
			.then().log().all()
			.extract();
	}

	private Member singUp() {
		return memberRepository.save(FixtureFactory.createMember());
	}

	private Product createProduct(Member member) {
		return productRepository.save(FixtureFactory.createProduct(member));
	}
}
