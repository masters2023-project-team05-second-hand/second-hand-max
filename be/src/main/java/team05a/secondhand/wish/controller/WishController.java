package team05a.secondhand.wish.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.member.resolver.MemberId;
import team05a.secondhand.wish.data.dto.WishRequest;
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
}
