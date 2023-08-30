package team05a.secondhand.member_address.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.member_address.data.entity.MemberAddress;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {

	List<MemberAddress> findByMemberId(Long id);
}
