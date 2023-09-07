package team05a.secondhand.category.data.dto;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.category.data.entity.Category;

import java.util.List;

@Getter
public class CategoriesResponse {

    private final List<CategoryResponse> categories;

    @Builder
    private CategoriesResponse(List<CategoryResponse> categories) {
        this.categories = categories;
    }



    public static CategoriesResponse from(List<Category> categories) {
        return CategoriesResponse.builder()
            .categories(CategoryResponse.from(categories))
            .build();
    }
}
