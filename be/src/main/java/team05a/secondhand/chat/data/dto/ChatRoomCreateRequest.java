package team05a.secondhand.chat.data.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatRoomCreateRequest {

	@NotNull(message = "상품 아이디는 있어야 합니다.")
	private Long productId;
	@NotNull(message = "첫 메세지는 있어야 합니다.")
	private String message;

	@Builder
	private ChatRoomCreateRequest(Long productId, String message) {
		this.productId = productId;
		this.message = message;
	}
}
