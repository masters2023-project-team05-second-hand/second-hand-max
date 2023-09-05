package team05a.secondhand.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.address.repository.AddressRepository;
import team05a.secondhand.member.data.dto.MemberAddressResponse;
import team05a.secondhand.member.data.dto.MemberAddressUpdateRequest;
import team05a.secondhand.member.data.dto.MemberResponse;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.member_address.data.entity.MemberAddress;
import team05a.secondhand.member_address.repository.MemberAddressRepository;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final MemberAddressRepository memberAddressRepository;

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
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payloadJwt = accessToken.split("\\.")[1];
        JsonParser jsonParser = new BasicJsonParser();
        String payload = new String(decoder.decode(payloadJwt));
        Map<String, Object> payloadJson = jsonParser.parseMap(payload);

        return (long) payloadJson.get("memberId");
    }
}
