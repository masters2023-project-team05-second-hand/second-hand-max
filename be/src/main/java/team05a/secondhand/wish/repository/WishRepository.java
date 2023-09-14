package team05a.secondhand.wish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.wish.data.entity.Wish;

public interface WishRepository extends JpaRepository<Wish, Long> {

	boolean existsByMemberIdAndProductId(Long memberId, Long productId);

	void deleteByMemberIdAndProductId(Long memberId, Long productId);
}
