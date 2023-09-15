package team05a.secondhand.product.data.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.product.data.entity.Product;

@Getter
@NoArgsConstructor
public class ProductReadResponse {

	private Long productId;
	private Long sellerId;
	private String thumbnailUrl;
	private String title;
	private String addressName;
	private Timestamp createdTime;
	private Integer price;
	private Long statusId;
	private ProductListStatsResponse stats;

	@Builder
	private ProductReadResponse(Long productId, Long sellerId, String thumbnailUrl, String title, String addressName,
		Timestamp createdTime, Integer price, Long statusId, ProductListStatsResponse stats) {
		this.productId = productId;
		this.sellerId = sellerId;
		this.thumbnailUrl = thumbnailUrl;
		this.title = title;
		this.addressName = addressName;
		this.createdTime = createdTime;
		this.price = price;
		this.statusId = statusId;
		this.stats = stats;
	}

	public static List<ProductReadResponse> from(List<Product> products) {
		return products.stream()
			.map(product -> ProductReadResponse.builder()
				.productId(product.getId())
				.sellerId(product.getMember().getId())
				.thumbnailUrl(product.getThumbnailUrl())
				.title(product.getTitle())
				.addressName(getAddressName(product.getAddress().getName()))
				.createdTime(product.getCreatedTime())
				.price(product.getPrice())
				.statusId(product.getStatus().getId())
				.stats(ProductListStatsResponse.builder().build())
				.build())
			.collect(Collectors.toList());
	}

	private static String getAddressName(String name) {
		return name.split(" ")[2];
	}
}
