package team05a.secondhand.address.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.member_address.data.entity.MemberAddress;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressResponse {

	private Long id;
	private String name;
	private boolean isLastVisited;

	public AddressResponse(Long id, String name, boolean isLastVisited) {
		this.id = id;
		this.name = name;
		this.isLastVisited = isLastVisited;
	}

	public static List<AddressResponse> from(List<MemberAddress> memberAddresses) {
		return memberAddresses.stream()
			.map(memberAddress -> new AddressResponse(memberAddress.getAddress().getId(),
				memberAddress.getAddress().getName(),
				memberAddress.isLastVisited()))
			.collect(Collectors.toList());
	}
}
