package team05a.secondhand.address.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.address.data.entity.Address;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ListAddressResponse {

	private Long id;
	private String name;

	@Builder
	private ListAddressResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static List<ListAddressResponse> from(List<Address> addresses) {
		return addresses.stream()
			.map(address -> new ListAddressResponse(address.getId(), address.getName()))
			.collect(Collectors.toList());
	}
}
