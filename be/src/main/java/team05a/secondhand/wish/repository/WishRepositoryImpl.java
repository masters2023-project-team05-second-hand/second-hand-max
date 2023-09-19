package team05a.secondhand.wish.repository;

import static team05a.secondhand.wish.data.entity.QWish.*;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.product.data.dto.ProductListResponse;
import team05a.secondhand.product.data.dto.ProductReadResponse;
import team05a.secondhand.product.data.entity.Product;

@RequiredArgsConstructor
public class WishRepositoryImpl implements WishRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Category> findWishCategoriesByMemberId(Long memberId) {
		List<Category> categories = jpaQueryFactory
			.selectDistinct(wish.product.category)
			.from(wish)
			.where(wish.member.id.eq(memberId))
			.fetch();

		categories.sort(Comparator.comparing(Category::getId));
		return categories;
	}

	@Override
	public ProductListResponse findWishProductsByMemberId(Long memberId, Pageable pageable) {
		List<Product> products = jpaQueryFactory
			.select(wish.product)
			.from(wish)
			.where(wish.member.id.eq(memberId))
			.orderBy(wish.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		Integer hasNext = jpaQueryFactory
			.selectOne()
			.from(wish)
			.where(wish.product.id.lt(getLastIndex(products)),
				wish.product.member.id.eq(memberId))
			.fetchOne();

		return ProductListResponse.builder()
			.products(ProductReadResponse.from(products))
			.hasNext(hasNext != null)
			.build();
	}

	@Override
	public ProductListResponse findWishProductsByMemberIdAndCategoryId(Long memberId, Long categoryId,
		Pageable pageable) {
		List<Product> products = jpaQueryFactory
			.select(wish.product)
			.from(wish)
			.where(
				wish.member.id.eq(memberId),
				wish.product.category.id.eq(categoryId)
			)
			.orderBy(wish.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		Integer hasNext = jpaQueryFactory
			.selectOne()
			.from(wish)
			.where(wish.product.id.lt(getLastIndex(products)),
				wish.product.member.id.eq(memberId),
				wish.product.category.id.in(categoryId))
			.fetchOne();

		return ProductListResponse.builder()
			.products(ProductReadResponse.from(products))
			.hasNext(hasNext != null)
			.build();
	}

	private Long getLastIndex(List<Product> products) {
		return products.isEmpty() ? 0 : products.get(products.size() - 1).getId();
	}
}
