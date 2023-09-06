package team05a.secondhand.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.image.data.entity.ProductImage;

public interface ImageRepository extends JpaRepository<ProductImage, Long> {
}
