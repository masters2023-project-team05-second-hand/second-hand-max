package team05a.secondhand.oauth_github.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.oauth_github.data.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
