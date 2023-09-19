package team05a.secondhand.wish.repository;

import static team05a.secondhand.wish.data.entity.QWish.*;

import java.util.Comparator;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.category.data.entity.Category;

@RequiredArgsConstructor
public class WishRepositoryImpl implements WishRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Category> findWishCategoriesByMemberId(Long memberId) {
		List<Category> categories = jpaQueryFactory
			.selectDistinct(wish.product.category)
			.from(wish)
			.innerJoin(wish.product.category)
			.where(wish.member.id.eq(memberId))
			.fetch();

		categories.sort(Comparator.comparing(Category::getId));
		return categories;
	}
}
