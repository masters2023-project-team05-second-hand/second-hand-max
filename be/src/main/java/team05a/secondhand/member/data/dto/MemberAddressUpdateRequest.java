package team05a.secondhand.member.data.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddressUpdateRequest {

	private List<Long> addressIds;

	@Builder
	private MemberAddressUpdateRequest(List<Long> addressIds) {
		this.addressIds = addressIds;
	}
}
