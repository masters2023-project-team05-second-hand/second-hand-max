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
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;
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

	@DisplayName("상품을 등록한다.")
	@Test
	void createProduct() {
		// given
		given(memberRepository.findById(any())).willReturn(Optional.of(FixtureFactory.createMember()));
		given(categoryRepository.findById(any())).willReturn(Optional.ofNullable(FixtureFactory.createCategory()));
		given(addressRepository.findById(any())).willReturn(Optional.ofNullable(FixtureFactory.createAddress()));
		given(statusRepository.findById(any())).willReturn(Optional.ofNullable(FixtureFactory.createStatus()));
		given(productRepository.save(any())).willReturn(FixtureFactory.createProductResponse());
		List<String> imageUrls = List.of("imageUrl");
		ProductCreateRequest productCreateRequest = FixtureFactory.productCreateRequest();

		//when
		Product product = productService.create(productCreateRequest, 1L, imageUrls);

		//then
		assertThat(product.getTitle()).isEqualTo(productCreateRequest.getTitle());
		assertThat(product.getContent()).isEqualTo(productCreateRequest.getContent());
	}
}
