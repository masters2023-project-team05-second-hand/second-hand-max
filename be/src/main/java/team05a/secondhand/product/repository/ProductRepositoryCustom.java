package team05a.secondhand.product.repository;

import team05a.secondhand.product.data.dto.ProductListResponse;

public interface ProductRepositoryCustom {

	ProductListResponse findAllByAddressIdAndCategoryId(Long addressId, Long categoryId, Long cursor, Long size);
}
