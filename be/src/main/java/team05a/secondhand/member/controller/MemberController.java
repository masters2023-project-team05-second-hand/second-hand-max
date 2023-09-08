package team05a.secondhand.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.member.data.dto.MemberAddressResponse;
import team05a.secondhand.member.data.dto.MemberAddressUpdateRequest;
import team05a.secondhand.member.data.dto.MemberResponse;
import team05a.secondhand.member.resolver.MemberId;
import team05a.secondhand.member.service.MemberService;

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

	@GetMapping("/addresses")
	public ResponseEntity<List<MemberAddressResponse>> getAddresses(@MemberId Long memberId) {
		List<MemberAddressResponse> memberAddressResponses = memberService.getMemberAddress(memberId);

		return ResponseEntity.ok()
			.body(memberAddressResponses);
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
