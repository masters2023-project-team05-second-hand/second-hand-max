package team05a.secondhand.product.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductUpdateStatusRequest {

	private Long statusId;

	@Builder
	public ProductUpdateStatusRequest(Long statusId) {
		this.statusId = statusId;
	}
}
