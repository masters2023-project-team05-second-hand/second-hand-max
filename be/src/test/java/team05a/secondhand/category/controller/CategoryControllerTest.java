package team05a.secondhand.category.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import team05a.secondhand.ControllerTestSupport;
import team05a.secondhand.fixture.FixtureFactory;

class CategoryControllerTest extends ControllerTestSupport {

	@DisplayName("카테고리 목록을 조회한다.")
	@Test
	void retrieveList() throws Exception {
		// given
		given(categoryService.findAll()).willReturn(FixtureFactory.createCategoryResponseList());

		//when & then
		mockMvc.perform(get("/api/categories"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").exists())
			.andExpect(jsonPath("$.[*].name").exists())
			.andDo(print());
	}
}
