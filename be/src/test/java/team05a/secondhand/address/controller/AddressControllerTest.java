package team05a.secondhand.address.controller;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import team05a.secondhand.address.service.AddressService;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.jwt.JwtTokenProvider;

@WebMvcTest(controllers = {AddressController.class})
@Import(value = {JwtTokenProvider.class})
class AddressControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	@MockBean
	protected AddressService addressService;

	@DisplayName("동네 목록을 조회한다.")
	@Test
	void retrieveList() throws Exception {
		// given
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", String.valueOf(0));
		queryParams.add("size", String.valueOf(10));
		given(addressService.findAll(any())).willReturn(FixtureFactory.createAddressListResponse());

		//when & then
		mockMvc.perform(get("/api/addresses")
				.queryParams(queryParams))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.addresses[*].id").exists())
			.andExpect(jsonPath("$.hasNext").exists())
			.andDo(print());
	}
}
