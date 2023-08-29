package team05a.secondhand.member_address.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MemberAddress {

	@Id
	@GeneratedValue
	private Long memberId;
	private Long addressId;

	public MemberAddress() {
	}

	public MemberAddress(Long memberId, Long addressId) {
		this.memberId = memberId;
		this.addressId = addressId;
	}

	public static MemberAddress from(Long memberId, Long addressId) {
		return new MemberAddress(memberId, addressId);
	}
}
