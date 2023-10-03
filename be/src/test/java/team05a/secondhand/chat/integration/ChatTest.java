package team05a.secondhand.chat.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import team05a.secondhand.DatabaseCleanup;
import team05a.secondhand.errors.exception.BuyerIdAndMessageSenderIdNotSameException;
import team05a.secondhand.errors.exception.SellerIdAndBuyerIdSameException;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private DatabaseCleanup databaseCleanup;

	@BeforeEach
	public void setUp() {
		databaseCleanup.execute();
	}

	@Test
	@DisplayName("채팅방을 만든다.")
	void createChatRoom() throws Exception {
		//given
		Member member = FixtureFactory.createMember();
		memberRepository.save(member);
		Product product = FixtureFactory.createProductRequest(member);
		productRepository.save(product);
		Member sender = FixtureFactory.createAnotherMember();
		memberRepository.save(sender);
		String accessToken = jwtTokenProvider.createAccessToken(Map.of("memberId", sender.getId()));
		String requestBody = "{\"productId\": 1,\"message\": {\"senderId\": " + sender.getId()
			+ ",\"content\": \"얼마야?\"}}";

		//when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders
					.post("/api/chat/room")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody)
			)
			.andDo(print());

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.roomId").isNotEmpty());
	}

	@Test
	@DisplayName("채팅방을 만들려할 때 구매자 아이디와 메세지 전송자 아이디가 같지 않으면 예외를 던진다.")
	void createChatRoomWithNotSameBuyerIdAndSenderId() throws Exception {
		//given
		Member member = FixtureFactory.createMember();
		memberRepository.save(member);
		Product product = FixtureFactory.createProductRequest(member);
		productRepository.save(product);
		Member sender = FixtureFactory.createAnotherMember();
		memberRepository.save(sender);
		String accessToken = jwtTokenProvider.createAccessToken(Map.of("memberId", sender.getId()));
		String requestBody = "{\"productId\": 1,\"message\": {\"senderId\": " + (sender.getId() + 1)
			+ ",\"content\": \"얼마야?\"}}";
		BuyerIdAndMessageSenderIdNotSameException exception = new BuyerIdAndMessageSenderIdNotSameException();

		//when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders
					.post("/api/chat/room")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody)
			)
			.andDo(print());

		//given
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message")
				.value(exception.getMessage()));
	}

	@Test
	@DisplayName("채팅방을 만들려할 때 구매자 아이디와 판매자 아이디가 같으면 예외를 던진다.")
	void createChatRoomWithNotSameBuyerIdAndSellerId() throws Exception {
		//given
		Member member = FixtureFactory.createMember();
		memberRepository.save(member);
		Product product = FixtureFactory.createProductRequest(member);
		productRepository.save(product);
		String accessToken = jwtTokenProvider.createAccessToken(Map.of("memberId", member.getId()));
		String requestBody = "{\"productId\": 1,\"message\": {\"senderId\": " + member.getId()
			+ ",\"content\": \"얼마야?\"}}";
		SellerIdAndBuyerIdSameException exception = new SellerIdAndBuyerIdSameException();

		//when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders
					.post("/api/chat/room")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody)
			)
			.andDo(print());

		//given
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message")
				.value(exception.getMessage()));
	}
}
