package team05a.secondhand.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;
import team05a.secondhand.member.data.dto.MemberResponse;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse getMember(String accessToken) {
        long memberId = getMemberIdFromAccessToken(accessToken);
        Optional<Member> member = memberRepository.findById(memberId);

        return member.map(MemberResponse::from).orElse(null);
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
