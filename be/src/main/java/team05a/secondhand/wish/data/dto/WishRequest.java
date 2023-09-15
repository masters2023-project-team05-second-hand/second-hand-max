package team05a.secondhand.wish.data.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.wish.data.entity.Wish;

@NoArgsConstructor
@Getter
public class WishRequest {

	@NotNull
	private Long productId;
	@NotNull
	private Boolean isWished;

	@Builder
	private WishRequest(Long productId, Boolean isWished) {
		this.productId = productId;
		this.isWished = isWished;
	}
}
