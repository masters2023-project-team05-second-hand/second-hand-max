package team05a.secondhand.image.data.dto;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.image.data.entity.ProductImage;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductImageResponse {

    private final Long id;
    private final String url;

    @Builder
    private ProductImageResponse(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public static List<ProductImageResponse> from(List<ProductImage> productImages) {
        return productImages.stream()
            .map(ProductImageResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    private static ProductImageResponse from(ProductImage productImage) {
        return ProductImageResponse.builder()
            .id(productImage.getId())
            .url(productImage.getImageUrl())
            .build();
    }
}
