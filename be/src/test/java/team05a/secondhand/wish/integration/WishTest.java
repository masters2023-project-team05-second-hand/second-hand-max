package team05a.secondhand.wish.integration;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import team05a.secondhand.AcceptanceTest;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;
import team05a.secondhand.wish.repository.WishRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class WishTest{

	@Autowired
	MockMvc mockMvc;
	@Autowired
	WishRepository wishRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	ObjectMapper objectMapper;

	@Test
	@Transactional
	@DisplayName("위시를 만든다")
	void createWish() throws Exception {
		//given
		Member member = FixtureFactory.createMember();
		memberRepository.save(member);
		Product product = FixtureFactory.createProductRequest(member);
		productRepository.save(product);
		String accessToken = jwtTokenProvider.createAccessToken(Map.of("memberId", member.getId()));
		String jsonBody = objectMapper.writeValueAsString(Map.of("productId", product.getId(), "isWished", true));

		//when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders
					.post("/api/members/wishlist")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonBody)
			)
			.andDo(print());

		//then
		resultActions
			.andExpect(status().isOk());
		assertThat(wishRepository.existsByMemberIdAndProductId(member.getId(), product.getId())).isTrue();
	}

	@Test
	@Transactional
	@DisplayName("위시를 삭제 한다.")
	void deleteWish() throws Exception {
		//given
		Member member = FixtureFactory.createMember();
		memberRepository.save(member);
		Product product = FixtureFactory.createProductRequest(member);
		productRepository.save(product);
		String accessToken = jwtTokenProvider.createAccessToken(Map.of("memberId", member.getId()));
		String createJsonBody = objectMapper.writeValueAsString(Map.of("productId", product.getId(), "isWished", true));
		String deleteJsonBody = objectMapper.writeValueAsString(Map.of("productId", product.getId(), "isWished", false));
		mockMvc.perform(
			MockMvcRequestBuilders
				.post("/api/members/wishlist")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(createJsonBody)
		);

		//when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders
				.post("/api/members/wishlist")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(deleteJsonBody)
		);

		//then
		resultActions
			.andExpect(status().isOk());
		assertThat(wishRepository.existsByMemberIdAndProductId(member.getId(), product.getId())).isFalse();
	}
}
