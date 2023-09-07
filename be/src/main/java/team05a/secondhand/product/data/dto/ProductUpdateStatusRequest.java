package team05a.secondhand.product.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductUpdateStatusRequest {

	private final Long statusId;

	@Builder
	public ProductUpdateStatusRequest(Long statusId) {
		this.statusId = statusId;
	}
}
