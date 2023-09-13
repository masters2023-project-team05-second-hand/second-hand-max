package team05a.secondhand.product.data.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.product.data.entity.Product;

@Getter
public class ProductReadResponse {

	private final Long productId;
	private final Long sellerId;
	private final String thumbnailUrl;
	private final String title;
	private final String addressName;
	private final Timestamp createdTime;
	private final Integer price;
	private final Long statusId;

	@Builder
	private ProductReadResponse(Long productId, Long sellerId, String thumbnailUrl, String title, String addressName,
		Timestamp createdTime, Integer price, Long statusId) {
		this.productId = productId;
		this.sellerId = sellerId;
		this.thumbnailUrl = thumbnailUrl;
		this.title = title;
		this.addressName = addressName;
		this.createdTime = createdTime;
		this.price = price;
		this.statusId = statusId;
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
				.build())
			.collect(Collectors.toList());
	}

	private static String getAddressName(String name) {
		return name.split(" ")[2];
	}
}
