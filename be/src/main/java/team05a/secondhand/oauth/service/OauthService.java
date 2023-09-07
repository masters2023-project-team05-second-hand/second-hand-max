package team05a.secondhand.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team05a.secondhand.jwt.Jwt;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.member_refreshtoken.data.entity.MemberRefreshToken;
import team05a.secondhand.member_refreshtoken.repository.MemberRefreshTokenRepository;
import team05a.secondhand.oauth.data.dto.JwtResponse;
import team05a.secondhand.oauth.data.dto.MemberOauthRequest;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OauthService {

    private final MemberRepository memberRepository;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponse login(MemberOauthRequest memberOauthRequest) {
        Member member = save(memberOauthRequest);
        Jwt jwt = jwtTokenProvider.createJwt(Map.of("memberId", member.getId()));
        memberRefreshTokenRepository.save(new MemberRefreshToken(member, jwt.getRefreshToken()));

        return JwtResponse.builder().tokens(jwt).build();
    }

    private Member save(MemberOauthRequest memberOauthRequest) {
        return memberRepository.findByEmailAndType(memberOauthRequest.getEmail(), memberOauthRequest.getType())
                .orElseGet(() -> memberRepository.save(Member.from(memberOauthRequest)));
    }
}
