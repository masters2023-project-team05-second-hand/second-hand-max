package team05a.secondhand.product.data.dto;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.member.data.entity.Member;

@Getter
public class ProductSellerResponse {

	private final Long id;
	private final String nickname;

	@Builder
	private ProductSellerResponse(Long id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}

	public static ProductSellerResponse from(Member member) {
		return ProductSellerResponse.builder()
			.id(member.getId())
			.nickname(member.getNickname())
			.build();
	}
}
