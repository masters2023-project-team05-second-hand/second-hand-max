package team05a.secondhand.fixture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import team05a.secondhand.address.data.dto.AddressListResponse;
import team05a.secondhand.address.data.dto.ListAddressResponse;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.category.data.dto.CategoryResponse;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.oauth.OauthAttributes;
import team05a.secondhand.oauth.data.dto.MemberOauthRequest;
import team05a.secondhand.oauth.data.dto.OauthRefreshTokenRequest;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.dto.ProductIdResponse;
import team05a.secondhand.product.data.dto.ProductListResponse;
import team05a.secondhand.product.data.dto.ProductReadResponse;
import team05a.secondhand.product.data.dto.ProductStatusResponse;
import team05a.secondhand.product.data.dto.ProductUpdateRequest;
import team05a.secondhand.product.data.dto.ProductUpdateStatusRequest;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.status.data.entity.Status;

public class FixtureFactory {

	public static AddressListResponse createAddressListResponse() {
		List<ListAddressResponse> addresses = new ArrayList<>();
		addresses.add(ListAddressResponse.builder().id(1L).name("서울특별시 강남구 역삼1동").build());
		addresses.add(ListAddressResponse.builder().id(2L).name("서울특별시 강남구 역삼2동").build());
		return AddressListResponse.builder()
			.addresses(addresses)
			.hasNext(true)
			.build();
	}

	public static List<CategoryResponse> createCategoryResponseList() {
		List<CategoryResponse> categories = new ArrayList<>();
		categories.add(new CategoryResponse(1L, "기타 중고물품", "https://i.ibb.co/tCyMPf5/etc.png"));
		categories.add(new CategoryResponse(2L, "인기매물", "https://i.ibb.co/LSkHKbL/star.png"));
		return categories;
	}

	public static Product createProductRequest(Member member) {
		return Product.builder()
			.title("제목")
			.content("내용")
			.thumbnailUrl("http://")
			.member(member)
			.address(createAddress())
			.category(createCategory())
			.build();
	}

	public static Product createProductResponse() {
		return Product.builder()
			.id(1L)
			.title("제목")
			.content("내용")
			.thumbnailUrl("http://")
			.build();
	}

	public static ProductCreateRequest productCreateRequestWithMultipartFile() throws IOException {
		return ProductCreateRequest.builder()
			.title("제목")
			.content("내용")
			.images(List.of(getMultipartFile()))
			.categoryId(1L)
			.addressId(1L)
			.build();
	}

	public static ProductCreateRequest productCreateRequest() {
		return ProductCreateRequest.builder()
			.title("제목")
			.content("내용")
			.categoryId(1L)
			.addressId(1L)
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

	public static Member createAnotherMember() {
		return Member.builder()
			.type(OauthAttributes.KAKAO)
			.email("nag@google.com")
			.nickname("nag")
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

	public static ProductIdResponse createProductIdResponse() {
		return ProductIdResponse.builder()
			.productId(1L)
			.build();
	}

	public static ProductUpdateRequest productUpdateRequest() {
		return ProductUpdateRequest.builder()
			.title("update")
			.content("update")
			.build();
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

	private static MultipartFile getMultipartFile() throws IOException {
		File tempFile = File.createTempFile("create", "jpeg");
		FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(tempFile.toPath()), false,
			tempFile.getName(), (int)tempFile.length(), tempFile.getParentFile());
		InputStream input = new FileInputStream(tempFile);
		OutputStream os = fileItem.getOutputStream();
		IOUtils.copy(input, os);
		return new CommonsMultipartFile(fileItem);
	}

	public static ProductImage createProductImage(Product product) {
		return ProductImage.builder()
			.product(product)
			.imageUrl("https://50000eggplant-image/test.jpeg")
			.build();
	}

	public static ProductStatusResponse createProductStatusResponse() {
		return ProductStatusResponse.builder()
			.statusId(1L)
			.build();
	}

	public static ProductUpdateStatusRequest createProductUpdateStatusRequest() {
		return ProductUpdateStatusRequest.builder()
			.statusId(1L)
			.build();
	}

	public static MemberOauthRequest createMemberRequest() {
		return MemberOauthRequest.builder()
			.email("wis@naver.com")
			.profileImgUrl("imageUrl")
			.nickname("wisdom")
			.type(OauthAttributes.GITHUB.name())
			.build();
	}

	public static OauthRefreshTokenRequest createMockOauthRefreshTokenRequest() {
		return OauthRefreshTokenRequest.builder()
			.refreshToken("refreshToken")
			.build();
	}

	public static ProductListResponse createProductListResponse() {
		List<ProductReadResponse> productReadResponseList = new ArrayList<>();
		productReadResponseList.add(ProductReadResponse.builder()
			.productId(1L)
			.sellerId(1L)
			.statusId(1L)
			.createdTime(Timestamp.from(Instant.now()))
			.price(null)
			.title("제목")
			.build());

		return ProductListResponse.builder()
			.products(productReadResponseList)
			.build();
	}

	public static Product createProductRequestForRepo(Member member, Address address, Category category,
		Status status) {
		return Product.builder()
			.title("제목")
			.content("내용")
			.thumbnailUrl("http://")
			.member(member)
			.address(address)
			.category(category)
			.status(status)
			.build();
	}
}
