package team05a.secondhand.member.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team05a.secondhand.member.data.dto.MemberResponse;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.oauth.OauthAttributes;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

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
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + 1000000L);
        String accessToken = Jwts.builder()
                .setClaims(Map.of("memberId", 1L))
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(Keys.hmacShaKeyFor("ThisIsSecretKeyForTestDoYouKnowThatIAmNag?".getBytes()))
                .compact();
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        //when
        MemberResponse memberResponse = memberService.getMember(accessToken);

        //then
        assertThat(memberResponse.getNickname()).isEqualTo("nickname");
        assertThat(memberResponse.getProfileImgUrl()).isEqualTo("profile");
    }
}
