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

	private final Boolean isSeller;
	@JsonProperty("product")
	private final ProductDetailResponse productDetail;
	private final List<ProductImageResponse> images;
	private final List<StatusResponse> statuses;

	@Builder
	private ProductResponse(boolean isSeller, ProductDetailResponse productDetail, List<ProductImageResponse> images,
		List<StatusResponse> statuses) {
		this.isSeller = isSeller;
		this.productDetail = productDetail;
		this.images = images;
		this.statuses = statuses;
	}

	public static ProductResponse from(Long memberId, Product product, List<ProductImage> productImages,
		List<Status> statuses) {
		return ProductResponse.builder()
			.isSeller(memberId.equals(product.getMember().getId()))
			.productDetail(ProductDetailResponse.from(product))
			.images(ProductImageResponse.from(productImages))
			.statuses(StatusResponse.from(statuses))
			.build();
	}
}
