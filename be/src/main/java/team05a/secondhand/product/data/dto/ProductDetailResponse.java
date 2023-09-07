package team05a.secondhand.product.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.status.data.entity.Status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ProductDetailResponse {

    private final String seller;
    private final String category;
    private final String title;
    private final String contents;
    private final String price;
    private final Timestamp createdTime;
    @JsonProperty("status")
    private final Long statusId;

    @Builder
    private ProductDetailResponse(String seller, String category, String title, String contents, String price, Timestamp createdTime, Long statusId) {
        this.seller = seller;
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.createdTime = createdTime;
        this.statusId = statusId;
    }

    public static ProductDetailResponse from(Product product) {
        return ProductDetailResponse.builder()
            .seller(product.getMember().getNickname())
            .category(product.getCategory().getName())
            .title(product.getTitle())
            .contents(product.getContent())
            .price(product.getPrice())
            .createdTime(product.getCreatedTime())
            .statusId(product.getStatus().getId())
            .build();
    }
}
