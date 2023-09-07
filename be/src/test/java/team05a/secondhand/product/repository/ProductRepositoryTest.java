package team05a.secondhand.product.repository;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import team05a.secondhand.AcceptanceTest;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.product.data.entity.Product;

class ProductRepositoryTest extends AcceptanceTest {

	@Autowired
	private ProductRepository productRepository;

	@DisplayName("상품을 등록한다.")
	@Test
	void save() {
		//given & when
		Product product = FixtureFactory.createProductRequest();
		Product save = productRepository.save(product);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(save.getTitle()).isEqualTo(product.getTitle());
			softAssertions.assertThat(save.getContent()).isEqualTo(product.getContent());
			softAssertions.assertThat(save.getThumbnailUrl()).isEqualTo(product.getThumbnailUrl());
		});
	}

	@DisplayName("상품 아이디와 멤버로 상품이 존재하는지 확인한다.")
	@Test
	void existsByIdAndMember() {
		//given & when
		Product product = FixtureFactory.createProductRequest();
		Product save = productRepository.save(product);
		boolean exists = productRepository.existsByIdAndMember(save.getId(), save.getMember());

		//then
		assertThat(exists).isTrue();
	}
}
