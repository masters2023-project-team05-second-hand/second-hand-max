package team05a.secondhand.oauth_github.data.dto;

import lombok.Getter;

@Getter
public class MemberOauthRequest {

	private final String oauthId;
	private final String nickname;
	private final String email;
	private final String profileUrl;
	private final String type;

	public MemberOauthRequest(String oauthId, String nickname, String email, String profileUrl, String type) {
		this.oauthId = oauthId;
		this.nickname = nickname;
		this.email = email;
		this.profileUrl = profileUrl;
		this.type = type;
	}
}
