package team05a.secondhand.member.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.address.repository.AddressRepository;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.data.dto.MemberAddressResponse;
import team05a.secondhand.member.data.dto.MemberAddressUpdateRequest;
import team05a.secondhand.member.data.dto.MemberResponse;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.member_address.data.entity.MemberAddress;
import team05a.secondhand.member_address.repository.MemberAddressRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final MemberAddressRepository memberAddressRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberResponse getMember(String accessToken) {
        long memberId = getMemberIdFromAccessToken(accessToken);
        Optional<Member> member = memberRepository.findById(memberId);

        return member.map(MemberResponse::from).orElse(null);
    }

    @Transactional
    public List<MemberAddressResponse> updateMemberAddresses(String accessToken,
                                                             MemberAddressUpdateRequest memberAddressUpdateRequest) {
        long memberId = getMemberIdFromAccessToken(accessToken);
        memberAddressRepository.deleteByMemberId(memberId);
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
        List<Address> addresses = memberAddressUpdateRequest.getAddressIds().stream()
                .map(id -> addressRepository.findById(id).orElseThrow(RuntimeException::new))
                .collect(Collectors.toList());
        List<MemberAddress> memberAddresses = memberAddressRepository.saveAll(MemberAddress.of(member, addresses));

        return MemberAddressResponse.from(memberAddresses);
    }

    private long getMemberIdFromAccessToken(String accessToken) {
        Claims claims = jwtTokenProvider.getClaims(accessToken);
        Integer intMemberId = (Integer) claims.get("memberId");

        return intMemberId.longValue();
    }
}
