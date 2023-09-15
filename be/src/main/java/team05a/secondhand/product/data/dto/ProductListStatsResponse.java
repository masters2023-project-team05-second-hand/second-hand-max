package team05a.secondhand.product.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductListStatsResponse {

	private int chatCount;
	private int wishCount;

	@Builder
	private ProductListStatsResponse(int chatCount, int wishCount) {
		this.chatCount = chatCount;
		this.wishCount = wishCount;
	}
}
