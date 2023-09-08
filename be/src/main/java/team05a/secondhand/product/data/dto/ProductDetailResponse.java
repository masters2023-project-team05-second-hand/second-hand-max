package team05a.secondhand.product.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.address.data.dto.ProductAddressResponse;
import team05a.secondhand.category.data.dto.ProductCategoryResponse;
import team05a.secondhand.product.data.entity.Product;

import java.sql.Timestamp;

@Getter
public class ProductDetailResponse {

    private final String seller;
    @JsonProperty("category")
    private final ProductCategoryResponse productCategoryResponse;
    @JsonProperty("address")
    private final ProductAddressResponse productAddressResponse;
    private final String title;
    private final String contents;
    private final Integer price;
    private final Timestamp createdTime;
    @JsonProperty("status")
    private final Long statusId;

    @Builder
    public ProductDetailResponse(String seller, ProductCategoryResponse productCategoryResponse, ProductAddressResponse productAddressResponse, String title, String contents, Integer price, Timestamp createdTime, Long statusId) {
        this.seller = seller;
        this.productCategoryResponse = productCategoryResponse;
        this.productAddressResponse = productAddressResponse;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.createdTime = createdTime;
        this.statusId = statusId;
    }

    public static ProductDetailResponse from(Product product) {
        return ProductDetailResponse.builder()
            .seller(product.getMember().getNickname())
            .productCategoryResponse(ProductCategoryResponse.from(product.getCategory()))
            .productAddressResponse(ProductAddressResponse.from(product.getAddress()))
            .title(product.getTitle())
            .contents(product.getContent())
            .price(product.getPrice())
            .createdTime(product.getCreatedTime())
            .statusId(product.getStatus().getId())
            .build();
    }
}
