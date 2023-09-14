package team05a.secondhand.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.product.data.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

	boolean existsByIdAndMemberId(Long productId, Long memberId);
}
