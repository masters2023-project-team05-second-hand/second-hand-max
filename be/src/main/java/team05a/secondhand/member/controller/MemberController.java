package team05a.secondhand.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team05a.secondhand.member.data.dto.MemberAddressResponse;
import team05a.secondhand.member.data.dto.MemberAddressUpdateRequest;
import team05a.secondhand.member.data.dto.MemberResponse;
import team05a.secondhand.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberResponse> getMember(HttpServletRequest request) {
        String accessToken = getAccessToken(request);
        MemberResponse memberResponse = memberService.getMember(accessToken);

        return ResponseEntity.ok()
                .body(memberResponse);
    }

    @PutMapping("/addresses")
    public ResponseEntity<List<MemberAddressResponse>> updateAddresses(HttpServletRequest httpServletRequest,
                                                                       @RequestBody MemberAddressUpdateRequest memberAddressUpdateRequest) {
        String accessToken = getAccessToken(httpServletRequest);
        List<MemberAddressResponse> memberAddressResponses = memberService.updateMemberAddresses(accessToken,
                memberAddressUpdateRequest);

        return ResponseEntity.ok()
                .body(memberAddressResponses);
    }

    private String getAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return authorizationHeader.replaceAll("^Bearer( )*", "");
    }

}
