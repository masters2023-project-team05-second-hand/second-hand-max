package team05a.secondhand.member.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import team05a.secondhand.oauth_github.data.dto.MemberOauthRequest;

@Entity
public class Member {

	@Id
	@GeneratedValue
	private Long id;
	private String type;
	private String nickname;
	private String email;
	private String profileUrl;

	public Member() {
	}

	public Member(String type, String nickname, String email, String profileUrl) {
		this.type = type;
		this.nickname = nickname;
		this.email = email;
		this.profileUrl = profileUrl;
	}

	public Long getId() {
		return id;
	}

	public static Member from(MemberOauthRequest memberOauthRequest) {
		return new Member(memberOauthRequest.getType(), memberOauthRequest.getNickname(),
			memberOauthRequest.getEmail(), memberOauthRequest.getProfileUrl());
	}
}
