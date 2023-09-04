package team05a.secondhand.fixture;

import java.util.ArrayList;
import java.util.List;

import team05a.secondhand.address.data.dto.AddressListResponse;
import team05a.secondhand.address.data.dto.ListAddressResponse;
import team05a.secondhand.category.data.dto.CategoryResponse;

public class FixtureFactory {

	public static AddressListResponse createAddressListResponse() {
		List<ListAddressResponse> addresses = new ArrayList<>();
		addresses.add(new ListAddressResponse(1L, "서울특별시 강남구 역삼1동"));
		addresses.add(new ListAddressResponse(2L, "서울특별시 강남구 역삼2동"));
		return new AddressListResponse(addresses, true);
	}

	public static List<CategoryResponse> createCategoryResponseList() {
		List<CategoryResponse> categories = new ArrayList<>();
		categories.add(new CategoryResponse(1L, "기타 중고물품", "https://i.ibb.co/tCyMPf5/etc.png"));
		categories.add(new CategoryResponse(2L, "인기매물", "https://i.ibb.co/LSkHKbL/star.png"));
		return categories;
	}
}
