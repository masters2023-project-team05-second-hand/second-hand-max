package team05a.secondhand.member.integration;

import static groovy.json.JsonOutput.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.data.dto.MemberAddressUpdateRequest;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.member_address.data.entity.MemberAddress;
import team05a.secondhand.member_address.repository.MemberAddressRepository;
import team05a.secondhand.oauth.OauthAttributes;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberAddressRepository memberAddressRepository;

	@Test
	@Transactional
	@DisplayName("멤버의 주소를 업데이트한다.")
	void updateMemberAddress() throws Exception {
		//given
		Member member = Member.builder()
			.profileImgUrl("profile")
			.email("email")
			.nickname("nickname")
			.type(OauthAttributes.GITHUB)
			.build();
		memberRepository.save(member);
		String accessToken = jwtTokenProvider.createAccessToken(Map.of("memberId", member.getId()));
		MemberAddressUpdateRequest request = MemberAddressUpdateRequest.builder()
			.addressIds(List.of(5L, 10L))
			.build();

		//when
		ResultActions resultActions = mockMvc.perform(put("/api/members/addresses")
			.header("Authorization", "Bearer " + accessToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(toJson(request)));

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpectAll(
				jsonPath("$.length()").value(2),
				jsonPath("$[0].name").value("서울특별시 강남구 논현2동")
			);
	}

	@Test
	@Transactional
	@DisplayName("멤버의 주소를 가져온다.")
	void getMemberAddress() throws Exception {
		//given
		Member member = FixtureFactory.createMember();
		memberRepository.save(member);
		List<Address> addresses = FixtureFactory.createAddresses();
		memberAddressRepository.saveAll(MemberAddress.of(member, addresses));
		String accessToken = jwtTokenProvider.createAccessToken(Map.of("memberId", member.getId()));

		//when
		ResultActions resultActions = mockMvc.perform(get("/api/members/addresses")
			.header("Authorization", "Bearer " + accessToken));

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpectAll(
				jsonPath("$.length()").value(2),
				jsonPath("$[1].name").value("서울특별시 강남구 압구정동")
			);
	}
}

