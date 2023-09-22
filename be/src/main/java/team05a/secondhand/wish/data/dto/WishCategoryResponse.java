package team05a.secondhand.wish.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.category.data.entity.Category;

@Getter
public class WishCategoryResponse {

	private final Long id;
	private final String name;

	@Builder
	private WishCategoryResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static List<WishCategoryResponse> from(List<Category> categories) {
		return categories.stream()
			.map(WishCategoryResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}

	private static WishCategoryResponse from(Category category) {
		return WishCategoryResponse.builder()
			.id(category.getId())
			.name(category.getName())
			.build();
	}
}
