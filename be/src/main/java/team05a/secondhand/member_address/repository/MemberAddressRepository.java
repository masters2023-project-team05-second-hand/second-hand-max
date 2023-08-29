package team05a.secondhand.member_address.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.member_address.data.entity.MemberAddress;
import team05a.secondhand.oauth_github.data.dto.AddressResponse;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {
	List<AddressResponse> findByMemberId(Long id);
}
