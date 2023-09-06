package team05a.secondhand.image.repository;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import team05a.secondhand.AcceptanceTest;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;

class ImageRepositoryTest extends AcceptanceTest {

	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private ProductRepository productRepository;

	@DisplayName("상품 이미지를 등록한다.")
	@Test
	void save() {
		//given & when
		Product product = productRepository.save(FixtureFactory.createProductRequest());
		List<ProductImage> productImages = FixtureFactory.createProductImageListRequest(product);
		List<ProductImage> save = imageRepository.saveAll(productImages);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(save.get(0).getProduct().getTitle())
				.isEqualTo(productImages.get(0).getProduct().getTitle());
			softAssertions.assertThat(save.get(0).getImageUrl()).isEqualTo(productImages.get(0).getImageUrl());
		});
	}
}
