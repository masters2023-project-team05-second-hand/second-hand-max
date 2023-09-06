package team05a.secondhand.product.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import team05a.secondhand.ControllerTestSupport;
import team05a.secondhand.errors.handler.GlobalExceptionHandler;
import team05a.secondhand.fixture.FixtureFactory;

class ProductControllerTest extends ControllerTestSupport {

	@DisplayName("상품을 등록한다.")
	@Test
	void create() throws Exception {
		// mocking
		mockingMemberId();

		// given
		given(productService.create(any(), any(), any())).willReturn(FixtureFactory.createProductResponse());

		//when & then
		mockMvc.perform(multipart("/api/products")
				.param("title", "title")
				.param("content", "content"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").exists())
			.andDo(print());
	}

	private void mockingMemberId() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService, imageService))
			.setControllerAdvice(new GlobalExceptionHandler())
			.setCustomArgumentResolvers(loginArgumentResolver)
			.build();

		Long memberId = 1L;
		when(loginArgumentResolver.supportsParameter(any()))
			.thenReturn(true);
		when(loginArgumentResolver.resolveArgument(any(), any(), any(), any()))
			.thenReturn(memberId);
	}
}
