package team05a.secondhand.product.data.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.status.data.entity.Status;

@Getter
public class ProductCreateRequest {

	private final Long categoryId;
	private final Long addressId;
	@NotBlank(message = "제목은 필수입니다.")
	@Size(min = 1, max = 50, message = "제목은 1글자 이상, 50글자 이하여야 합니다.")
	private final String title;
	@NotBlank(message = "내용은 필수입니다.")
	@Size(min = 1, max = 10000, message = "내용은 1글자 이상, 10000글자 이하여야 합니다.")
	private final String content;
	private final Integer price;
	private final List<MultipartFile> images;

	@Builder
	private ProductCreateRequest(Long categoryId, Long addressId, String title, String content, Integer price,
		List<MultipartFile> images) {
		this.categoryId = categoryId;
		this.addressId = addressId;
		this.title = title;
		this.content = content;
		this.price = price;
		this.images = images;
	}

	public Product toEntity(Member member, Category category, Address address, Status status, String thumbnailUrl) {
		return Product.builder()
			.member(member)
			.category(category)
			.address(address)
			.status(status)
			.title(title)
			.content(content)
			.price(price)
			.thumbnailUrl(thumbnailUrl)
			.build();
	}
}
