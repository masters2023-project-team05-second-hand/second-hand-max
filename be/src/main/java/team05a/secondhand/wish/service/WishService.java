package team05a.secondhand.wish.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.errors.exception.MemberNotFoundException;
import team05a.secondhand.errors.exception.ProductNotFoundException;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;
import team05a.secondhand.wish.data.dto.WishCategoryResponse;
import team05a.secondhand.wish.data.dto.WishRequest;
import team05a.secondhand.wish.data.dto.WishResponse;
import team05a.secondhand.wish.data.entity.Wish;
import team05a.secondhand.wish.repository.WishRepository;

@Service
@RequiredArgsConstructor
public class WishService {

	private final WishRepository wishRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;

	@Transactional
	public void handleWishRequest(Long memberId, WishRequest wishRequest) {
		if (doesNeedToCreateWish(memberId, wishRequest)) {
			Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
			Product product = productRepository.findById(wishRequest.getProductId())
				.orElseThrow(ProductNotFoundException::new);
			Wish wish = Wish.builder()
				.product(product)
				.member(member)
				.build();

			wishRepository.save(wish);
			return;
		}
		if (doesNeedToDeleteWish(memberId, wishRequest)) {
			wishRepository.deleteByMemberIdAndProductId(memberId, wishRequest.getProductId());
		}
	}

	@Transactional(readOnly = true)
	public List<WishCategoryResponse> getWishCategories(Long memberId) {
		return WishCategoryResponse.from(wishRepository.findWishCategoriesByMemberId(memberId));
	}

	@Transactional(readOnly = true)
	public WishResponse getWish(Long memberId, Long productId) {
		return WishResponse.builder()
			.isWished(wishRepository.existsByMemberIdAndProductId(memberId, productId))
			.build();
	}

	private boolean doesNeedToCreateWish(Long memberId, WishRequest wishRequest) {
		return !wishRepository.existsByMemberIdAndProductId(memberId, wishRequest.getProductId())
			&& wishRequest.getIsWished();
	}

	private boolean doesNeedToDeleteWish(Long memberId, WishRequest wishRequest) {
		return wishRepository.existsByMemberIdAndProductId(memberId, wishRequest.getProductId())
			&& !wishRequest.getIsWished();
	}
}
