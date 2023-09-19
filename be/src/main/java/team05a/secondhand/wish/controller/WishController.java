package team05a.secondhand.wish.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.member.resolver.MemberId;
import team05a.secondhand.wish.data.dto.WishCategoryResponse;
import team05a.secondhand.wish.data.dto.WishRequest;
import team05a.secondhand.wish.data.dto.WishResponse;
import team05a.secondhand.wish.service.WishService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/wishlist")
public class WishController {

	private final WishService wishService;

	@PostMapping()
	public void createWish(@MemberId Long memberId, @RequestBody WishRequest wishRequest) {
		wishService.handleWishRequest(memberId, wishRequest);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<WishResponse> readWish(@MemberId Long memberId, @PathVariable("productId") Long productId) {
		WishResponse wishResponse = wishService.getWish(memberId, productId);

		return ResponseEntity.ok()
			.body(wishResponse);
	}

	@GetMapping("/categories")
	public ResponseEntity<List<WishCategoryResponse>> readWishCategories(@MemberId Long memberId) {
		List<WishCategoryResponse> wishCategoryResponses = wishService.getWishCategories(memberId);

		return ResponseEntity.ok()
			.body(wishCategoryResponses);
	}
}
