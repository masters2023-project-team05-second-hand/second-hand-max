package team05a.secondhand.product.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDetailStatsResponse {

	private final Long chatCount;
	private final Long wishCount;
	private final Long viewCount;

	@Builder
	public ProductDetailStatsResponse(Long chatCount, Long wishCount, Long viewCount) {
		this.chatCount = chatCount;
		this.wishCount = wishCount;
		this.viewCount = viewCount;
	}

	public static ProductDetailStatsResponse from(Long chatCount, Long wishCount, Long viewCount) {
		return ProductDetailStatsResponse.builder()
			.chatCount(chatCount)
			.wishCount(wishCount)
			.viewCount(viewCount)
			.build();
	}
}
