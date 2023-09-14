package team05a.secondhand.product.repository;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import team05a.secondhand.AcceptanceTest;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.address.repository.AddressRepository;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.category.repository.CategoryRepository;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.image.repository.ImageRepository;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.dto.ProductListResponse;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.status.data.entity.Status;
import team05a.secondhand.status.repository.StatusRepository;

class ProductRepositoryTest extends AcceptanceTest {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private StatusRepository statusRepository;

	@DisplayName("상품을 등록한다.")
	@Test
	void save() {
		//given & when
		Member member = memberRepository.save(FixtureFactory.createMember());
		Product product = FixtureFactory.createProductRequest(member);
		Product save = productRepository.save(product);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(save.getTitle()).isEqualTo(product.getTitle());
			softAssertions.assertThat(save.getContent()).isEqualTo(product.getContent());
			softAssertions.assertThat(save.getThumbnailUrl()).isEqualTo(product.getThumbnailUrl());
		});
	}

	@DisplayName("상품 아이디와 사용자 아이디로 판매자가 사용자인지 확인한다.")
	@Test
	void existsByIdAndMember() {
		//given & when
		Member member = memberRepository.save(FixtureFactory.createMember());
		Product product = FixtureFactory.createProductRequest(member);
		Product save = productRepository.save(product);
		boolean exists = productRepository.existsByIdAndMemberId(save.getId(), save.getMember().getId());

		//then
		assertThat(exists).isTrue();
	}

	@DisplayName("상품을 저장 후 삭제한다.")
	@Test
	void delete() {
		//given
		Member member = memberRepository.save(FixtureFactory.createMember());
		Product product = FixtureFactory.createProductRequest(member);
		Product save = productRepository.save(product);
		imageRepository.save(FixtureFactory.createProductImage(product));

		// when
		productRepository.deleteById(save.getId());

		//then
		assertThat(productRepository.findById(save.getId()).isEmpty()).isTrue();
		assertThat(imageRepository.findFirstByProductId(save.getId()).isEmpty()).isTrue();
	}

	@Transactional
	@DisplayName("상품 목록 조회를 한다.")
	@Test
	void findList() {
		//given
		Member member = memberRepository.save(FixtureFactory.createMember());
		Address address = addressRepository.findById(1L).orElseThrow();
		Category category = categoryRepository.findById(1L).orElseThrow();
		Status status = statusRepository.findById(1L).orElseThrow();
		Product product = FixtureFactory.createProductRequestForRepo(member, address, category, status);
		Product save = productRepository.save(product);
		Long addressId = save.getAddress().getId();
		Long categoryId = save.getCategory().getId();

		// when
		ProductListResponse response = productRepository.findList(addressId, categoryId, 0L, 10L);

		//then
		assertThat(response.getProducts().get(0).getProductId()).isEqualTo(save.getId());
		assertThat(response.isHasNext()).isFalse();
	}
}
