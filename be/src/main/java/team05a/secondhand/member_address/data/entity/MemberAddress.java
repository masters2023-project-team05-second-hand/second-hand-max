package team05a.secondhand.member_address.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.member.data.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_address_id")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;
	private boolean isLastVisited;

	@Builder
	private MemberAddress(Long id, Member member, Address address, boolean isLastVisited) {
		this.id = id;
		this.member = member;
		this.address = address;
		this.isLastVisited = isLastVisited;
	}

	public static List<MemberAddress> of(Member member, List<Address> addresses) {
		List<MemberAddress> memberAddresses = addresses.stream()
				.map(address -> MemberAddress.builder()
						.member(member)
						.address(address)
						.build())
				.collect(Collectors.toList());

		memberAddresses.get(memberAddresses.size() - 1).isLastVisited = true;

		return memberAddresses;
	}
}
