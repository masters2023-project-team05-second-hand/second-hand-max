package team05a.secondhand.chat.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.product.data.entity.Product;

@NoArgsConstructor
@Getter
public class ChatProductResponse {

	private Long productId;
	private String title;
	private String thumbnailUrl;
	private Integer price;
	private Long status;

	@Builder
	private ChatProductResponse(Long productId, String title, String thumbnailUrl, Integer price, Long status) {
		this.productId = productId;
		this.title = title;
		this.thumbnailUrl = thumbnailUrl;
		this.price = price;
		this.status = status;
	}

	public static ChatProductResponse from(Product product) {
		return ChatProductResponse.builder()
			.productId(product.getId())
			.title(product.getTitle())
			.thumbnailUrl(product.getThumbnailUrl())
			.price(product.getPrice())
			.status(product.getStatus().getId())
			.build();
	}
}
