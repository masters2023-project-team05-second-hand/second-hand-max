package team05a.secondhand.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team05a.secondhand.member.data.dto.MemberAddressResponse;
import team05a.secondhand.member.data.dto.MemberAddressUpdateRequest;
import team05a.secondhand.member.data.dto.MemberResponse;
import team05a.secondhand.member.resolver.MemberId;
import team05a.secondhand.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberResponse> getMember(@MemberId Long memberId) {
        MemberResponse memberResponse = memberService.getMember(memberId);

        return ResponseEntity.ok()
            .body(memberResponse);
    }

    @PutMapping("/addresses")
    public ResponseEntity<List<MemberAddressResponse>> updateAddresses(@MemberId Long memberId,
                                                                       @RequestBody MemberAddressUpdateRequest memberAddressUpdateRequest) {
        List<MemberAddressResponse> memberAddressResponses = memberService.updateMemberAddresses(memberId,
            memberAddressUpdateRequest);

        return ResponseEntity.ok()
            .body(memberAddressResponses);
    }
}
