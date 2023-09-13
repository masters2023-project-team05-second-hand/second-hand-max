package team05a.secondhand.product.repository;

import static team05a.secondhand.product.data.entity.QProduct.*;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.product.data.dto.ProductListResponse;
import team05a.secondhand.product.data.dto.ProductReadResponse;
import team05a.secondhand.product.data.entity.Product;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public ProductListResponse findList(Long addressId, Long categoryId, Long cursor, Long size) {
		List<Product> products = jpaQueryFactory
			.selectFrom(product)
			.where(
				ltCursor(cursor),
				product.address.id.eq(addressId),
				eqCategoryId(categoryId))
			.orderBy(product.id.desc())
			.limit(size)
			.fetch();
		Integer hasNext = jpaQueryFactory
			.selectOne()
			.from(product)
			.where(product.id.lt(getLastIndex(products)),
				product.address.id.eq(addressId),
				eqCategoryId(categoryId))
			.fetchOne();

		return ProductListResponse.builder()
			.products(ProductReadResponse.from(products))
			.hasNext(hasNext != null)
			.build();
	}

	private Long getLastIndex(List<Product> products) {
		return products.size() == 0 ? 0 : products.get(products.size() - 1).getId();
	}

	private BooleanExpression ltCursor(Long cursor) {
		return cursor == 0 ? null : product.id.lt(cursor);
	}

	private BooleanExpression eqCategoryId(Long categoryId) {
		return categoryId == 0 ? null : product.category.id.eq(categoryId);
	}
}
