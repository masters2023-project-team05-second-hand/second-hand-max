package team05a.secondhand.oauth_github.data.dto;

import lombok.Getter;

@Getter
public class MemberRequest {

	private final String nickname;
	private final String email;
	private final String profileUrl;

	public MemberRequest(String nickname, String email, String profileUrl) {
		this.nickname = nickname;
		this.email = email;
		this.profileUrl = profileUrl;
	}
}
