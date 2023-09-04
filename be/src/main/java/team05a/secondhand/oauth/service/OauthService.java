package team05a.secondhand.oauth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team05a.secondhand.jwt.Jwt;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.member_address.repository.MemberAddressRepository;
import team05a.secondhand.member_refreshtoken.data.entity.MemberRefreshToken;
import team05a.secondhand.member_refreshtoken.repository.MemberRefreshTokenRepository;
import team05a.secondhand.oauth.data.dto.MemberOauthRequest;

import java.util.Map;

@Transactional
@Service
public class OauthService {

    private final MemberRepository memberRepository;
    private final MemberAddressRepository memberAddressRepository;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public OauthService(MemberRepository memberRepository, MemberAddressRepository memberAddressRepository,
                        MemberRefreshTokenRepository memberRefreshTokenRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.memberAddressRepository = memberAddressRepository;
        this.memberRefreshTokenRepository = memberRefreshTokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Jwt login(MemberOauthRequest memberOauthRequest) {
        Member member = save(memberOauthRequest);
//		MemberLoginResponse memberLoginResponse = MemberLoginResponse.from(member);
//		List<AddressResponse> addresses = AddressResponse.from(memberAddressRepository.findByMemberId(member.getId()));

        Jwt jwt = jwtTokenProvider.createJwt(Map.of("memberId", member.getId()));
        memberRefreshTokenRepository.save(new MemberRefreshToken(member, jwt.getRefreshToken()));

        return jwt;
    }

    private Member save(MemberOauthRequest memberOauthRequest) {
        return memberRepository.findByEmailAndType(memberOauthRequest.getEmail(), memberOauthRequest.getType())
                .orElseGet(() -> memberRepository.save(Member.from(memberOauthRequest)));
    }
}
