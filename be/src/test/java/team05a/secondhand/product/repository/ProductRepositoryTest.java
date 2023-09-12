package team05a.secondhand.product.repository;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.image.repository.ImageRepository;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.entity.Product;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ImageRepository imageRepository;

	@Transactional
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

	@Transactional
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

	@Transactional
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
}
