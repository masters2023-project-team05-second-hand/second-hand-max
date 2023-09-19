package team05a.secondhand.wish.repository;

import java.util.List;

import team05a.secondhand.category.data.entity.Category;

public interface WishRepositoryCustom {

	List<Category> findWishCategoriesByMemberId(Long memberId);
}
