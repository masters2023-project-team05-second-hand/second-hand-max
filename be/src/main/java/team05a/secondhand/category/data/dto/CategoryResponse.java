package team05a.secondhand.category.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.category.data.entity.Category;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryResponse {

	private Long id;
	private String name;
	private String ImgUrl;

	public CategoryResponse(Long id, String name, String imgUrl) {
		this.id = id;
		this.name = name;
		ImgUrl = imgUrl;
	}

	public static List<CategoryResponse> from(List<Category> categoryList) {
		return categoryList.stream()
			.map(category -> new CategoryResponse(category.getId(), category.getName(), category.getImgUrl()))
			.collect(Collectors.toList());
	}
}
