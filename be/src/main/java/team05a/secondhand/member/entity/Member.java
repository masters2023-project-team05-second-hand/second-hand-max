package team05a.secondhand.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.member_address.data.entity.MemberAddress;
import team05a.secondhand.oauth_github.data.dto.MemberOauthRequest;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	@Column(length = 10, nullable = false)
	private String type;
	@Column(length = 50, nullable = false)
	private String email;
	@Column(length = 50, nullable = false)
	private String nickname;
	@Column(length = 100)
	private String profileImgUrl;
	@OneToMany(mappedBy = "member")
	private List<MemberAddress> memberAddresses = new ArrayList<>();

	public Member(String type, String email, String nickname, String profileImgUrl) {
		this.type = type;
		this.email = email;
		this.nickname = nickname;
		this.profileImgUrl = profileImgUrl;
	}

	public static Member from(MemberOauthRequest memberOauthRequest) {
		return new Member(memberOauthRequest.getType(), memberOauthRequest.getEmail(), memberOauthRequest.getNickname(),
			memberOauthRequest.getProfileImgUrl());
	}
}
