package team05a.secondhand.wish.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.product.data.dto.ProductListResponse;

public interface WishRepositoryCustom {

	List<Category> findWishCategoriesByMemberId(Long memberId);

	ProductListResponse findWishProductsByMemberId(Long memberId, Pageable pageable);

	ProductListResponse findWishProductsByMemberIdAndCategoryId(Long memberId, Long categoryId, Pageable pageable);
}
