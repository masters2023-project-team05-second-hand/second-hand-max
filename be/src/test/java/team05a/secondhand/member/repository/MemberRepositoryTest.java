package team05a.secondhand.member.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.oauth.OauthAttributes;

import java.util.Optional;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 아이디로 멤버를 조회한다.")
    void findById() {
        //given & when
        Optional<Member> actual = memberRepository.findById(1L);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.get().getType()).isEqualTo(OauthAttributes.KAKAO);
            assertions.assertThat(actual.get().getEmail()).isEqualTo("nag@codesquad.kr");
        });
    }
}
