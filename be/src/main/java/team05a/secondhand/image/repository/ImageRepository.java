package team05a.secondhand.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.product.data.entity.Product;

public interface ImageRepository extends JpaRepository<ProductImage, Long> {

	Long countByProductId(Long productId);

	ProductImage findFirstByProduct(Product product);
}
