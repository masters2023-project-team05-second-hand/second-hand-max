package team05a.secondhand.category.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import team05a.secondhand.category.service.CategoryService;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.jwt.JwtTokenProvider;

@WebMvcTest(controllers = {CategoryController.class})
@Import(value = {JwtTokenProvider.class})
class CategoryControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	@MockBean
	protected CategoryService categoryService;

	@DisplayName("카테고리 목록을 조회한다.")
	@Test
	void retrieveList() throws Exception {
		// given
		given(categoryService.findAll()).willReturn(FixtureFactory.createCategoryResponseList());

		//when & then
		mockMvc.perform(get("/api/categories"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.categories.[*].id").exists())
			.andExpect(jsonPath("$.categories.[*].name").exists())
			.andDo(print());
	}
}
