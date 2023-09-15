package team05a.secondhand.product.data.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductListResponse {

	private List<ProductReadResponse> products;
	private ProductDetailStatsResponse stats;
	private boolean hasNext;

	@Builder
	private ProductListResponse(List<ProductReadResponse> products, boolean hasNext) {
		this.products = products;
		this.stats = ProductDetailStatsResponse.makeZeroStats();
		this.hasNext = hasNext;
	}
}
