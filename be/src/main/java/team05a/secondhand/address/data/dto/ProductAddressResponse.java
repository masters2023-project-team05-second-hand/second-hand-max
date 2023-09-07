package team05a.secondhand.address.data.dto;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.address.data.entity.Address;

@Getter
public class ProductAddressResponse {

    private Long id;
    private String name;

    @Builder
    private ProductAddressResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ProductAddressResponse from(Address address) {
        return ProductAddressResponse.builder()
            .id(address.getId())
            .name(address.getName())
            .build();
    }
}
