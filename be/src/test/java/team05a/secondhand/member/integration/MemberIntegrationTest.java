package team05a.secondhand.member.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.data.dto.MemberAddressUpdateRequest;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.oauth.OauthAttributes;

import java.util.List;
import java.util.Map;

import static groovy.json.JsonOutput.toJson;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MemberRepository memberRepository;

    @Test
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
}
