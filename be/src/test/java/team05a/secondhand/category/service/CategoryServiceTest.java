package team05a.secondhand.category.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import team05a.secondhand.category.data.dto.CategoryResponse;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.category.repository.CategoryRepository;
import team05a.secondhand.fixture.FixtureFactory;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@InjectMocks
	private CategoryService categoryService;
	@Mock
	private CategoryRepository categoryRepository;

	@DisplayName("카테고리 목록을 조회한다.")
	@Test
	void findAll() {
		// given
		Category category = FixtureFactory.createCategory();
		List<Category> categories = List.of(category);
		given(categoryRepository.findAll()).willReturn(categories);

		// when
		List<CategoryResponse> response = categoryService.findAll();

		// then
		assertThat(response.get(0).getId()).isEqualTo(category.getId());
		assertThat(response.get(0).getName()).isEqualTo(category.getName());
		assertThat(response.get(0).getImgUrl()).isEqualTo(category.getImgUrl());
	}
}
