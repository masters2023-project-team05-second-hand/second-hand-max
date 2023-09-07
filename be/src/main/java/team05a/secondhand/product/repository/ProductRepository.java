package team05a.secondhand.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.product.data.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	boolean existsByIdAndMember(Long productId, Member member);
}
