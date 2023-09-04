package team05a.secondhand.category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.category.data.dto.CategoryResponse;
import team05a.secondhand.category.repository.CategoryRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public List<CategoryResponse> findAll() {
		return CategoryResponse.from(categoryRepository.findAll());
	}
}
