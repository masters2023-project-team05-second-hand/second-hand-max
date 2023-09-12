package team05a.secondhand.image.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import team05a.secondhand.AcceptanceTest;
import team05a.secondhand.errors.exception.ImageNotFoundException;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;

class ImageRepositoryTest extends AcceptanceTest {

	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("상품 이미지를 등록한다.")
	@Test
	void save() {
		//given & when
		Member member = memberRepository.save(FixtureFactory.createMember());
		Product product = productRepository.save(FixtureFactory.createProductRequest(member));
		List<ProductImage> productImages = FixtureFactory.createProductImageListRequest(product);
		List<ProductImage> save = imageRepository.saveAll(productImages);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(save.get(0).getProduct().getTitle())
				.isEqualTo(productImages.get(0).getProduct().getTitle());
			softAssertions.assertThat(save.get(0).getImageUrl()).isEqualTo(productImages.get(0).getImageUrl());
		});
	}

	@DisplayName("상품 아이디로 이미지 개수를 가져온다.")
	@Test
	void countByProductId() {
		//given & when
		Member member = memberRepository.save(FixtureFactory.createMember());
		Product product = productRepository.save(FixtureFactory.createProductRequest(member));
		List<ProductImage> productImages = FixtureFactory.createProductImageListRequest(product);
		List<ProductImage> save = imageRepository.saveAll(productImages);
		Long count = imageRepository.countByProductId(product.getId());

		//then
		assertThat(count).isEqualTo(save.size());
	}

	@DisplayName("상품으로 첫번째 이미지를 찾는다.")
	@Test
	void findFirstByProduct() {
		//given & when
		Member member = memberRepository.save(FixtureFactory.createMember());
		Product product = productRepository.save(FixtureFactory.createProductRequest(member));
		List<ProductImage> productImages = FixtureFactory.createProductImageListRequest(product);
		List<ProductImage> save = imageRepository.saveAll(productImages);
		ProductImage productImage = imageRepository.findFirstByProductId(product.getId())
			.orElseThrow(ImageNotFoundException::new);

		//then
		assertThat(productImage.getId()).isEqualTo(save.get(0).getId());
		assertThat(productImage.getImageUrl()).isEqualTo(save.get(0).getImageUrl());
	}
}
