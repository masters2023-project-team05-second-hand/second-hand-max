package team05a.secondhand.category.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import team05a.secondhand.IntegrationTestSupport;
import team05a.secondhand.category.data.dto.CategoryResponse;

class CategoryRepositoryTest extends IntegrationTestSupport {

	@Autowired
	private CategoryRepository categoryRepository;

	@DisplayName("카테고리 목록을 조회한다.")
	@Test
	void findAll() {
		// given & when
		List<CategoryResponse> response = CategoryResponse.from(categoryRepository.findAll());

		// then
		assertThat(response.get(0).getId()).isEqualTo(1);
		assertThat(response.get(0).getName()).isEqualTo("기타 중고물품");
		assertThat(response.get(0).getImgUrl()).isEqualTo("https://i.ibb.co/tCyMPf5/etc.png");
	}
}