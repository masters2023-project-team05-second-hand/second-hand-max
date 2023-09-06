package team05a.secondhand.fixture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import team05a.secondhand.address.data.dto.AddressListResponse;
import team05a.secondhand.address.data.dto.ListAddressResponse;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.category.data.dto.CategoryResponse;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.member.data.dto.MemberAddressUpdateRequest;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.oauth.OauthAttributes;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.status.data.entity.Status;

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

	public static Product createProductRequest() {
		return Product.builder()
			.title("제목")
			.content("내용")
			.price("")
			.thumbnailUrl("http://")
			.build();
	}

	public static Product createProductResponse() {
		return Product.builder()
			.id(1L)
			.title("제목")
			.content("내용")
			.price("")
			.thumbnailUrl("http://")
			.build();
	}

	public static ProductCreateRequest productCreateRequest() {
		return ProductCreateRequest.builder()
			.title("제목")
			.content("내용")
			.price("")
			.build();
	}

	public static Member createMember() {
		return Member.builder()
			.type(OauthAttributes.GITHUB)
			.email("wis@naver.com")
			.nickname("wisdom")
			.profileImgUrl("imageUrl")
			.build();
	}

	public static Category createCategory() {
		return Category.builder()
			.id(1L)
			.name("기타 중고물품")
			.imgUrl("https://i.ibb.co/tCyMPf5/etc.png")
			.build();
	}

	public static Address createAddress() {
		return Address.builder()
			.id(1L)
			.name("서울특별시 강남구 역삼1동")
			.build();
	}

	public static Status createStatus() {
		return Status.builder()
			.id(1L)
			.name("판매중")
			.build();
	}

	public static List<ProductImage> createProductImageListResponse() {
		return Collections.singletonList(ProductImage.builder()
			.product(createProductResponse())
			.imageUrl("imageUrl")
			.build());
	}

	public static List<ProductImage> createProductImageListRequest(Product product) {
		return Collections.singletonList(ProductImage.builder()
			.product(product)
			.imageUrl("imageUrl")
			.build());
	}

	public static List<Address> createAddresses() {
		Address address1 = Address.builder()
			.id(5L)
			.name("서울특별시 강남구 논현2동")
			.build();
		Address address2 = Address.builder()
			.id(6L)
			.name("서울특별시 강남구 압구정동")
			.build();

		return List.of(address1, address2);
	}
}
