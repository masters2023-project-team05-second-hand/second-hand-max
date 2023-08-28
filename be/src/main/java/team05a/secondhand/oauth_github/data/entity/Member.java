package team05a.secondhand.oauth_github.data.entity;

import javax.persistence.Entity;

import team05a.secondhand.oauth_github.OauthAttributes;
import team05a.secondhand.oauth_github.data.dto.MemberRequest;

@Entity
public class Member {

	private Long id;
	private String type;
	private String nickname;
	private String email;
	private String profileUrl;

	public Member(String type, String nickname, String email, String profileUrl) {
		this.type = type;
		this.nickname = nickname;
		this.email = email;
		this.profileUrl = profileUrl;
	}

	public Member fromGitHub(MemberRequest memberRequest) {
		return new Member(OauthAttributes.GITHUB.name(), memberRequest.getNickname(), memberRequest.getEmail(), memberRequest.getProfileUrl());
	}
}
