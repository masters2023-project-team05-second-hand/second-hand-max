package team05a.secondhand.product.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import team05a.secondhand.product.data.dto.ProductListResponse;

public interface ProductRepositoryCustom {

	ProductListResponse findList(Long addressId, Long categoryId, Long cursor, Long size);

	ProductListResponse findSalesList(Long memberId, List<Long> statusId, Pageable pageable);
}
