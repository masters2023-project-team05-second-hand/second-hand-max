package team05a.secondhand.member.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.member.data.dto.MemberAddressResponse;
import team05a.secondhand.member.data.dto.MemberAddressUpdateRequest;
import team05a.secondhand.member.data.dto.MemberNicknameUpdateRequest;
import team05a.secondhand.member.data.dto.MemberResponse;
import team05a.secondhand.member.resolver.MemberId;
import team05a.secondhand.member.service.MemberService;
import team05a.secondhand.product.data.dto.ProductListResponse;
import team05a.secondhand.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;
	private final ProductService productService;

	@GetMapping
	public ResponseEntity<MemberResponse> getMember(@MemberId Long memberId) {
		MemberResponse memberResponse = memberService.getMember(memberId);

		return ResponseEntity.ok()
			.body(memberResponse);
	}

	@PatchMapping("nickname")
	public void updateMemberNickname(@MemberId Long memberId,
		@RequestBody @Valid MemberNicknameUpdateRequest memberNicknameUpdateRequest) {
		memberService.updateMemberNickname(memberId, memberNicknameUpdateRequest);

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

	@PatchMapping("/profile-image")
	public ResponseEntity<Map<String, String>> updateProfile(@MemberId Long memberId,
		@RequestParam("newProfileImg") @NotNull(message = "파일이 넣어주세요.") MultipartFile newProfileImg) {
		String updatedImgUrl = memberService.updateMemberProfile(memberId, newProfileImg);
		Map<String, String> responseBody = Map.of("updatedImgUrl", updatedImgUrl);

		return ResponseEntity.ok()
			.body(responseBody);
	}

	@GetMapping("/sales")
	public ResponseEntity<ProductListResponse> getSalesList(@MemberId Long memberId, @RequestParam List<Long> statusId,
		Pageable pageable) {
		ProductListResponse productListResponse = productService.readSalesList(memberId, statusId, pageable);

		return ResponseEntity.ok()
			.body(productListResponse);
	}
}
