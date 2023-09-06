package team05a.secondhand.product.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateResponse {

	private final Long productId;

	@Builder
	private ProductCreateResponse(Long productId) {
		this.productId = productId;
	}

	public static ProductCreateResponse from(Long id) {
		return new ProductCreateResponse(id);
	}
}
