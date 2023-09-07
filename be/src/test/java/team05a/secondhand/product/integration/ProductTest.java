package team05a.secondhand.product.integration;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.address.repository.AddressRepository;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.category.repository.CategoryRepository;
import team05a.secondhand.errors.exception.AddressNotFoundException;
import team05a.secondhand.errors.exception.CategoryNotFoundException;
import team05a.secondhand.errors.exception.StatusNotFoundException;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.image.service.ImageService;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;
import team05a.secondhand.status.data.entity.Status;
import team05a.secondhand.status.repository.StatusRepository;

public class ProductTest extends AcceptanceTest {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private ImageService imageService;

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

	@DisplayName("상품을 등록 후 삭제한다.")
	@Test
	void delete_success() throws IOException {
		// given
		Member member = singUp();
		Product product = createProduct(member);

		// when
		var response = delete(product, member);

		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softAssertions.assertThat(response.jsonPath().getLong("productId")).isNotNull();
		});
	}

	private ExtractableResponse<Response> delete(Product product, Member member) {
		return RestAssured
			.given().log().all()
			.pathParam("productId", product.getId())
			.header(HttpHeaders.AUTHORIZATION,
				"Bearer " + jwtTokenProvider.createAccessToken(Map.of("memberId", member.getId())))
			.when()
			.delete("/api/products/{productId}")
			.then().log().all()
			.extract();
	}

	private Member singUp() {
		return memberRepository.save(FixtureFactory.createMember());
	}

	private Product createProduct(Member member) throws IOException {
		ProductCreateRequest productCreateRequest = FixtureFactory.productCreateRequestWithMultipartFile();
		List<String> imageUrls = imageService.upload(productCreateRequest.getImages());
		Category category = categoryRepository.findById(productCreateRequest.getCategoryId())
			.orElseThrow(CategoryNotFoundException::new);
		Address address = addressRepository.findById(productCreateRequest.getAddressId())
			.orElseThrow(AddressNotFoundException::new);
		Status status = statusRepository.findById(1L).orElseThrow(StatusNotFoundException::new);
		Product product = productRepository.save(
			productCreateRequest.toEntity(member, category, address, status, imageUrls.get(0)));
		imageService.create(product, imageUrls);
		return product;
	}
}
