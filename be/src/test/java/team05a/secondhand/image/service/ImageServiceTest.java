package team05a.secondhand.image.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.image.repository.ImageRepository;
import team05a.secondhand.product.data.entity.Product;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

	@InjectMocks
	private ImageService imageService;
	@Mock
	private ImageRepository imageRepository;

	@DisplayName("상품을 등록할 때 상품 이미지를 등록한다.")
	@Test
	void createProductImage() {
		// given
		Product product = FixtureFactory.createProductResponse();
		List<String> imageUrls = List.of("imageUrl");
		given(imageRepository.saveAll(any())).willReturn(FixtureFactory.createProductImageListResponse());

		//when
		List<ProductImage> productImages = imageService.create(product, imageUrls);

		//then
		assertThat(productImages.get(0).getProduct().getTitle()).isEqualTo(product.getTitle());
		assertThat(productImages.get(0).getImageUrl()).isEqualTo(imageUrls.get(0));
	}
}
