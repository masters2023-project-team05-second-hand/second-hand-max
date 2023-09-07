package team05a.secondhand.image.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.product.data.entity.Product;

import java.util.List;

public interface ImageRepository extends JpaRepository<ProductImage, Long> {

	Long countByProductId(Long productId);

	Optional<ProductImage> findFirstByProductId(Long productId);

	List<ProductImage> findAllByProduct(Product product);
}
