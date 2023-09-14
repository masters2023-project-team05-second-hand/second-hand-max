package team05a.secondhand.product.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import team05a.secondhand.address.repository.AddressRepository;
import team05a.secondhand.category.repository.CategoryRepository;
import team05a.secondhand.errors.exception.AddressNotFoundException;
import team05a.secondhand.errors.exception.CategoryNotFoundException;
import team05a.secondhand.errors.exception.MemberNotFoundException;
import team05a.secondhand.errors.exception.ProductNotFoundException;
import team05a.secondhand.errors.exception.StatusNotFoundException;
import team05a.secondhand.errors.exception.UnauthorizedProductModificationException;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.image.service.ImageService;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.dto.ProductIdResponse;
import team05a.secondhand.product.data.dto.ProductListResponse;
import team05a.secondhand.product.data.dto.ProductStatusResponse;
import team05a.secondhand.product.data.dto.ProductUpdateRequest;
import team05a.secondhand.product.data.dto.ProductUpdateStatusRequest;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;
import team05a.secondhand.redis.repository.RedisRepository;
import team05a.secondhand.status.data.entity.Status;
import team05a.secondhand.status.repository.StatusRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@InjectMocks
	private ProductService productService;
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private AddressRepository addressRepository;
	@Mock
	private CategoryRepository categoryRepository;
	@Mock
	private StatusRepository statusRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ImageService imageService;
	@Mock
	private RedisRepository redisRepository;

	@DisplayName("상품을 등록한다.")
	@Test
	void createProduct() {
		// given
		ProductCreateRequest productCreateRequest = FixtureFactory.productCreateRequest();
		Product productResponse = FixtureFactory.createProductResponse();
		List<String> imageUrls = List.of("imageUrl");
		given(imageService.uploadProductImages(any())).willReturn(imageUrls);
		given(memberRepository.findById(any())).willReturn(Optional.of(FixtureFactory.createMember()));
		given(categoryRepository.findById(any())).willReturn(Optional.ofNullable(FixtureFactory.createCategory()));
		given(addressRepository.findById(any())).willReturn(Optional.ofNullable(FixtureFactory.createAddress()));
		given(statusRepository.findById(any())).willReturn(Optional.ofNullable(FixtureFactory.createStatus()));
		given(productRepository.save(any())).willReturn(productResponse);

		//when
		ProductIdResponse productIdResponse = productService.create(productCreateRequest, 1L);

		//then
		assertThat(productIdResponse.getProductId()).isEqualTo(productResponse.getId());
	}

	@DisplayName("상품을 수정한다.")
	@Test
	void updateProduct() {
		// given
		ProductUpdateRequest productUpdateRequest = FixtureFactory.productUpdateRequest();
		Product productResponse = FixtureFactory.createProductResponse();
		List<String> imageUrls = List.of("imageUrl");
		given(imageService.countImagesBy(any())).willReturn(1L);
		given(imageService.uploadNew(any(), any())).willReturn(imageUrls);
		given(productRepository.existsById(any())).willReturn(true);
		given(memberRepository.existsById(any())).willReturn(true);
		given(productRepository.existsByIdAndMemberId(any(), any())).willReturn(true);
		given(productRepository.findById(any())).willReturn(
			Optional.ofNullable(FixtureFactory.createProductResponse()));
		given(categoryRepository.findById(any())).willReturn(Optional.ofNullable(FixtureFactory.createCategory()));
		given(addressRepository.findById(any())).willReturn(Optional.ofNullable(FixtureFactory.createAddress()));

		//when
		ProductIdResponse productIdResponse = productService.update(productUpdateRequest, 1L, 1L);

		//then
		assertThat(productIdResponse.getProductId()).isEqualTo(productResponse.getId());
	}

	@DisplayName("상품을 수정 시 상품이 없을 경우 400 에러를 반환한다.")
	@Test
	void update_fail_ProductNotFound() {
		// given
		ProductUpdateRequest productUpdateRequest = FixtureFactory.productUpdateRequest();
		given(productRepository.existsById(any())).willReturn(false);

		// when & then
		assertThatThrownBy(() -> productService.update(productUpdateRequest, 1L, 1L)).isInstanceOf(
			ProductNotFoundException.class);
	}

	@DisplayName("상품을 수정 시 멤버가 없을 경우 400 에러를 반환한다.")
	@Test
	void update_fail_MemberNotFound() {
		// given
		ProductUpdateRequest productUpdateRequest = FixtureFactory.productUpdateRequest();
		given(productRepository.existsById(any())).willReturn(true);
		given(memberRepository.existsById(any())).willReturn(false);

		// when & then
		assertThatThrownBy(() -> productService.update(productUpdateRequest, 1L, 1L)).isInstanceOf(
			MemberNotFoundException.class);
	}

	@DisplayName("상품을 수정 시 멤버가 다를 경우 400 에러를 반환한다.")
	@Test
	void update_fail_UnauthorizedMember() {
		// given
		ProductUpdateRequest productUpdateRequest = FixtureFactory.productUpdateRequest();
		given(productRepository.existsById(any())).willReturn(true);
		given(memberRepository.existsById(any())).willReturn(true);
		given(productRepository.existsByIdAndMemberId(any(), any())).willReturn(false);

		// when & then
		assertThatThrownBy(() -> productService.update(productUpdateRequest, 1L, 1L)).isInstanceOf(
			UnauthorizedProductModificationException.class);
	}

	@DisplayName("상품을 수정 시 없는 카테고리일 경우 400 에러를 반환한다.")
	@Test
	void update_fail_CategoryNotFound() {
		// given
		ProductUpdateRequest productUpdateRequest = FixtureFactory.productUpdateRequest();
		List<String> imageUrls = List.of("imageUrl");
		given(productRepository.existsById(any())).willReturn(true);
		given(memberRepository.existsById(any())).willReturn(true);
		given(productRepository.existsByIdAndMemberId(any(), any())).willReturn(true);
		given(imageService.countImagesBy(any())).willReturn(1L);
		given(imageService.uploadNew(any(), any())).willReturn(imageUrls);
		given(productRepository.findById(any())).willReturn(
			Optional.ofNullable(FixtureFactory.createProductResponse()));
		given(categoryRepository.findById(any())).willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> productService.update(productUpdateRequest, 1L, 1L)).isInstanceOf(
			CategoryNotFoundException.class);
	}

	@DisplayName("상품을 수정 시 없는 주소일 경우 400 에러를 반환한다.")
	@Test
	void update_fail_AddressNotFoundException() {
		// given
		ProductUpdateRequest productUpdateRequest = FixtureFactory.productUpdateRequest();
		List<String> imageUrls = List.of("imageUrl");
		given(productRepository.existsById(any())).willReturn(true);
		given(memberRepository.existsById(any())).willReturn(true);
		given(productRepository.existsByIdAndMemberId(any(), any())).willReturn(true);
		given(imageService.countImagesBy(any())).willReturn(1L);
		given(imageService.uploadNew(any(), any())).willReturn(imageUrls);
		given(productRepository.findById(any())).willReturn(
			Optional.ofNullable(FixtureFactory.createProductResponse()));
		given(categoryRepository.findById(any())).willReturn(Optional.ofNullable(FixtureFactory.createCategory()));
		given(addressRepository.findById(any())).willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> productService.update(productUpdateRequest, 1L, 1L)).isInstanceOf(
			AddressNotFoundException.class);
	}

	@DisplayName("상품의 상태를 수정한다.")
	@Test
	void updateProductStatus() {
		// given
		ProductUpdateStatusRequest productUpdateStatusRequest = FixtureFactory.createProductUpdateStatusRequest();
		Status status = FixtureFactory.createStatus();
		given(productRepository.existsById(any())).willReturn(true);
		given(memberRepository.existsById(any())).willReturn(true);
		given(productRepository.existsByIdAndMemberId(any(), any())).willReturn(true);
		given(productRepository.findById(any())).willReturn(
			Optional.ofNullable(FixtureFactory.createProductResponse()));
		given(statusRepository.findById(any())).willReturn(Optional.ofNullable(status));

		//when
		ProductStatusResponse productStatusResponse = productService.updateStatus(productUpdateStatusRequest, 1L, 1L);

		//then
		assertThat(productStatusResponse.getStatusId()).isEqualTo(productUpdateStatusRequest.getStatusId());
	}

	@DisplayName("상품의 상태 수정 시 응답받은 상태 아이디가 데이터베이스에 없는 경우 400 에러를 반환한다.")
	@Test
	void updateProductStatus_StatusNotFound() {
		// given
		ProductUpdateStatusRequest productUpdateStatusRequest = FixtureFactory.createProductUpdateStatusRequest();
		given(productRepository.existsById(any())).willReturn(true);
		given(memberRepository.existsById(any())).willReturn(true);
		given(productRepository.existsByIdAndMemberId(any(), any())).willReturn(true);
		given(productRepository.findById(any())).willReturn(
			Optional.ofNullable(FixtureFactory.createProductResponse()));
		given(statusRepository.findById(any())).willReturn(Optional.empty());

		//when & then
		assertThatThrownBy(() -> productService.updateStatus(productUpdateStatusRequest, 1L, 1L)).isInstanceOf(
			StatusNotFoundException.class);
	}

	@DisplayName("상품을 삭제한다.")
	@Test
	void delete_success() {
		// given
		Product productResponse = FixtureFactory.createProductResponse();
		given(productRepository.existsById(any())).willReturn(true);
		given(memberRepository.existsById(any())).willReturn(true);
		given(productRepository.existsByIdAndMemberId(any(), any())).willReturn(true);

		//when
		ProductIdResponse productIdResponse = productService.delete(productResponse.getId(), 1L);

		//then
		assertThat(productIdResponse.getProductId()).isEqualTo(productResponse.getId());
	}

	@DisplayName("상품 삭제 시 상품이 없을 경우 400 에러를 반환한다.")
	@Test
	void delete_fail_ProductNotFound() {
		// given
		given(productRepository.existsById(any())).willReturn(false);

		//when & then
		assertThatThrownBy(() -> productService.delete(1L, 1L)).isInstanceOf(
			ProductNotFoundException.class);
	}

	@DisplayName("상품을 삭제 시 멤버가 없을 경우 400 에러를 반환한다.")
	@Test
	void delete_fail_MemberNotFound() {
		// given
		given(productRepository.existsById(any())).willReturn(true);
		given(memberRepository.existsById(any())).willReturn(false);

		// when & then
		assertThatThrownBy(() -> productService.delete(1L, 1L)).isInstanceOf(
			MemberNotFoundException.class);
	}

	@DisplayName("상품을 삭제 시 멤버가 다를 경우 400 에러를 반환한다.")
	@Test
	void delete_fail_UnauthorizedMember() {
		// given
		given(productRepository.existsById(any())).willReturn(true);
		given(memberRepository.existsById(any())).willReturn(true);
		given(productRepository.existsByIdAndMemberId(any(), any())).willReturn(false);

		// when & then
		assertThatThrownBy(() -> productService.delete(1L, 1L)).isInstanceOf(
			UnauthorizedProductModificationException.class);
	}

	@DisplayName("상품의 목록을 조회한다.")
	@Test
	void readList() {
		// given
		ProductListResponse productListResponse = FixtureFactory.createProductListResponse();
		given(productRepository.findList(any(), any(), any(), any())).willReturn(productListResponse);

		//when
		ProductListResponse response = productService.readList(1L, 1L, 0L, 5L);

		//then
		assertThat(response.isHasNext()).isEqualTo(productListResponse.isHasNext());
		assertThat(response.getProducts().get(0).getProductId()).isEqualTo(
			productListResponse.getProducts().get(0).getProductId());
	}
}
