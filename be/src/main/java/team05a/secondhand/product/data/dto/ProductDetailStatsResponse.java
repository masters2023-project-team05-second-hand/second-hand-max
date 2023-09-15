package team05a.secondhand.product.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDetailStatsResponse {

	private final Integer chatCount;
	private final Integer wishCount;
	private final Integer viewCount;

	@Builder
	public ProductDetailStatsResponse(Integer chatCount, Integer wishCount, Integer viewCount) {
		this.chatCount = chatCount;
		this.wishCount = wishCount;
		this.viewCount = viewCount;
	}

	public static ProductDetailStatsResponse makeZeroStats() {
		return ProductDetailStatsResponse.builder()
			.chatCount(0)
			.wishCount(0)
			.viewCount(0)
			.build();
	}

}
