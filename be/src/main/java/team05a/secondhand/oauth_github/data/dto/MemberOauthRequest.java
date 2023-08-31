package team05a.secondhand.oauth_github.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.oauth_github.OauthAttributes;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberOauthRequest {

	private String nickname;
	private String email;
	private String profileImgUrl;
	private OauthAttributes type;

	public MemberOauthRequest(String nickname, String email, String profileImgUrl, String type) {
		this.nickname = nickname;
		this.email = email;
		this.profileImgUrl = profileImgUrl;
		this.type = OauthAttributes.validateOauthAttributes(type);
	}
}
