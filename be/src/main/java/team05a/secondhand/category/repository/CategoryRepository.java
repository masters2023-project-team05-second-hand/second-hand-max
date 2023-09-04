package team05a.secondhand.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.category.data.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
