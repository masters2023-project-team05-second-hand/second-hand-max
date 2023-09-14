package team05a.secondhand.wish.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WishResponse {

	private final Boolean isWished;

	@Builder
	private WishResponse(Boolean isWished) {
		this.isWished = isWished;
	}
}
