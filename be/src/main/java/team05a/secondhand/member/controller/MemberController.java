package team05a.secondhand.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team05a.secondhand.member.data.dto.MemberResponse;
import team05a.secondhand.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberResponse> getMember(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = authorizationHeader.replaceAll("^Bearer( )*", "");
        MemberResponse memberResponse = memberService.getMember(accessToken);

        return ResponseEntity.ok()
                .body(memberResponse);
    }
}
