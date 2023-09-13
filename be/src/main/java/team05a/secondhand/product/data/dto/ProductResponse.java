package team05a.secondhand.product.data.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.image.data.dto.ProductImageResponse;
import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.status.data.dto.StatusResponse;
import team05a.secondhand.status.data.entity.Status;

@Getter
public class ProductResponse {

	@JsonProperty("product")
	private final ProductDetailResponse productDetail;
	private final List<ProductImageResponse> images;

	@Builder
	private ProductResponse(ProductDetailResponse productDetail, List<ProductImageResponse> images) {
		this.productDetail = productDetail;
		this.images = images;
	}

	public static ProductResponse from(Product product, List<ProductImage> productImages) {
		return ProductResponse.builder()
			.productDetail(ProductDetailResponse.from(product))
			.images(ProductImageResponse.from(productImages))
			.build();
	}
}
