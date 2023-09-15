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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import team05a.secondhand.errors.handler.GlobalExceptionHandler;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.resolver.LoginArgumentResolver;
import team05a.secondhand.product.data.dto.ProductListResponse;
import team05a.secondhand.product.data.dto.ProductStatusResponse;
import team05a.secondhand.product.data.dto.ProductUpdateStatusRequest;
import team05a.secondhand.product.service.ProductService;

@WebMvcTest(controllers = {ProductController.class})
@Import(value = {JwtTokenProvider.class})
class ProductControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;
	@MockBean
	protected ProductService productService;
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

	@DisplayName("상품을 삭제한다.")
	@Test
	void deleteProduct() throws Exception {
		// mocking
		mockingMemberId();

		// given
		given(productService.delete(any(), any())).willReturn(FixtureFactory.createProductIdResponse());

		//when & then
		mockMvc.perform(delete("/api/products/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").exists())
			.andDo(print());
	}

	@DisplayName("상품의 상태를 수정한다.")
	@Test
	void updateStatus() throws Exception {
		// mocking
		mockingMemberId();
		ProductUpdateStatusRequest productUpdateStatusRequest = FixtureFactory.createProductUpdateStatusRequest();
		ProductStatusResponse productStatusResponse = FixtureFactory.createProductStatusResponse();

		// given
		given(productService.updateStatus(any(), any(), any())).willReturn(productStatusResponse);

		//when & then
		mockMvc.perform(patch("/api/products/1/status")
				.content(objectMapper.writeValueAsString(productUpdateStatusRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.statusId").exists())
			.andDo(print());
	}

	@DisplayName("상품 목록을 조회한다.")
	@Test
	void readList() throws Exception {
		// mocking
		mockingMemberId();
		ProductListResponse productListResponse = FixtureFactory.createProductListResponse();

		// given
		given(productService.readList(any(), any(), any(), any())).willReturn(productListResponse);

		//when & then
		mockMvc.perform(get("/api/products")
				.queryParam("addressId", "1")
				.queryParam("categoryId", "1")
				.queryParam("cursor", "0")
				.queryParam("size", "2"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	private void mockingMemberId() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService))
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
