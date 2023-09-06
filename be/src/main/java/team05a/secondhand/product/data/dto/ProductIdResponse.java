package team05a.secondhand.product.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductIdResponse {

	private final Long productId;

	@Builder
	private ProductIdResponse(Long productId) {
		this.productId = productId;
	}

	public static ProductIdResponse from(Long id) {
		return new ProductIdResponse(id);
	}
}
