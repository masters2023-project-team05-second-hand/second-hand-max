package team05a.secondhand.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.oauth_github.OauthAttributes;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmailAndType(String email, OauthAttributes type);
}
