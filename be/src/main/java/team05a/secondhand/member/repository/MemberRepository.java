package team05a.secondhand.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmailAndType(String email, String type);
}
