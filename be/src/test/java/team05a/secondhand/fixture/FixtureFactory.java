package team05a.secondhand.fixture;

import java.util.ArrayList;
import java.util.List;

import team05a.secondhand.address.data.dto.AddressListResponse;
import team05a.secondhand.address.data.entity.Address;

public class FixtureFactory {

	public static AddressListResponse createAddressListResponse() {
		List<Address> addresses = new ArrayList<>();
		addresses.add(new Address(1L, "서울특별시 강남구 역삼1동"));
		addresses.add(new Address(2L, "서울특별시 강남구 역삼2동"));
		return new AddressListResponse(addresses, true);
	}
}
