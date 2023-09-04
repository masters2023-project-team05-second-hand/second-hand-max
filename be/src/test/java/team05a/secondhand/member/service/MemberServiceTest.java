package team05a.secondhand.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.data.dto.MemberResponse;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.oauth.OauthAttributes;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("accessToken을 입력받고 Member를 MemberResponse로 변환한다.")
    void findMember() {
        //given
        Member member = new Member(OauthAttributes.KAKAO, "email", "nickname", "profile");
        String accessToken = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJtZW1iZXJJZCI6MSwiaWF0IjoxNjkzODM5MTg3LCJleHAiOjE2OTM4NDI3ODd9" +
                ".-2KqmmnKAgyPILeqyGOlrKKRJ5vzPaK_SEJpmLDvBSU";
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        //when
        MemberResponse memberResponse = memberService.getMember(accessToken);

        //then
        assertThat(memberResponse.getNickname()).isEqualTo("nickname");
        assertThat(memberResponse.getProfileImgUrl()).isEqualTo("profile");
    }
}
