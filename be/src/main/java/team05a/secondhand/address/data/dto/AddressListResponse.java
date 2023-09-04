package team05a.secondhand.address.data.dto;

import java.util.List;

import org.springframework.data.domain.Slice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.address.data.entity.Address;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressListResponse {

	private List<Address> addresses;
	private boolean hasNext;

	public AddressListResponse(List<Address> addresses, boolean hasNext) {
		this.addresses = addresses;
		this.hasNext = hasNext;
	}

	public static AddressListResponse from(Slice<Address> addresses) {
		return new AddressListResponse(addresses.toList(), addresses.hasNext());
	}
}
