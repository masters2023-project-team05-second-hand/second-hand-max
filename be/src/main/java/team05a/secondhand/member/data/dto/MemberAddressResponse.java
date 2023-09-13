package team05a.secondhand.member.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.member_address.data.entity.MemberAddress;

@Getter
public class MemberAddressResponse {

	private final Long id;
	private final String name;

	@Builder
	private MemberAddressResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static List<MemberAddressResponse> from(List<MemberAddress> memberAddresses) {
		return memberAddresses.stream()
			.map(MemberAddressResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}

	public static MemberAddressResponse from(MemberAddress memberAddress) {
		return MemberAddressResponse.builder()
			.id(memberAddress.getAddress().getId())
			.name(memberAddress.getAddress().getName())
			.build();
	}
}
