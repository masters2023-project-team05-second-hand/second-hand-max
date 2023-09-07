package team05a.secondhand.image.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.image.data.entity.ProductImage;

public interface ImageRepository extends JpaRepository<ProductImage, Long> {

	Long countByProductId(Long productId);

	Optional<ProductImage> findFirstByProductId(Long productId);
}
