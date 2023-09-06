package team05a.secondhand.product.controller;

import static org.mockito.ArgumentMatchers.*;
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
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import team05a.secondhand.errors.handler.GlobalExceptionHandler;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.image.service.ImageService;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.resolver.LoginArgumentResolver;
import team05a.secondhand.product.service.ProductService;

@WebMvcTest(controllers = {ProductController.class})
@Import(value = {JwtTokenProvider.class})
class ProductControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	@MockBean
	protected ProductService productService;
	@MockBean
	protected ImageService imageService;
	@MockBean
	protected LoginArgumentResolver loginArgumentResolver;

	@DisplayName("상품을 등록한다.")
	@Test
	void create() throws Exception {
		// mocking
		mockingMemberId();

		// given
		given(productService.create(any(), any())).willReturn(FixtureFactory.createProductIdResponse());

		//when & then
		mockMvc.perform(multipart("/api/products")
				.param("title", "title")
				.param("content", "content"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").exists())
			.andDo(print());
	}

	@DisplayName("상품을 수정한다.")
	@Test
	void update() throws Exception {
		// mocking
		mockingMemberId();

		// given
		given(productService.update(any(), any(), any())).willReturn(FixtureFactory.createProductIdResponse());

		//when & then
		mockMvc.perform(multipart(HttpMethod.PATCH, "/api/products/1")
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
