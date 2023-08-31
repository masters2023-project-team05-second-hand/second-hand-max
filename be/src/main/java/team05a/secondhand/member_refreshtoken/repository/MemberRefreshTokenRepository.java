package team05a.secondhand.member_refreshtoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.member_refreshtoken.data.entity.MemberRefreshToken;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {
}
