package team05a.secondhand.category.data.dto;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.category.data.entity.Category;

@Getter
public class ProductCategoryResponse {

    private final Long id;
    private final String name;

    @Builder
    private ProductCategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ProductCategoryResponse from(Category category) {
        return ProductCategoryResponse.builder()
            .id(category.getId())
            .name(category.getName())
            .build();
    }
}
