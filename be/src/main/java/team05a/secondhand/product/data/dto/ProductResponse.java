package team05a.secondhand.product.data.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.image.data.dto.ProductImageResponse;
import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.product.data.entity.Product;

@Getter
public class ProductResponse {

	@JsonProperty("product")
	private final ProductDetailResponse productDetail;
	private final List<ProductImageResponse> images;
	private final ProductDetailStatsResponse stats;

	@Builder
	private ProductResponse(ProductDetailResponse productDetail, List<ProductImageResponse> images,
		ProductDetailStatsResponse stats) {
		this.productDetail = productDetail;
		this.images = images;
		this.stats = stats;
	}

	public static ProductResponse from(Product product, List<ProductImage> productImages,
		ProductDetailStatsResponse productDetailStatsResponse) {
		return ProductResponse.builder()
			.productDetail(ProductDetailResponse.from(product))
			.images(ProductImageResponse.from(productImages))
			.stats(productDetailStatsResponse)
			.build();
	}
}
