package team05a.secondhand.product.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductStatusResponse {

	private final Long statusId;

	@Builder
	public ProductStatusResponse(Long statusId) {
		this.statusId = statusId;
	}

	public static ProductStatusResponse from(Long id) {
		return new ProductStatusResponse(id);
	}
}
