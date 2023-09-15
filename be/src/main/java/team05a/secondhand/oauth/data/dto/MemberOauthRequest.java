package team05a.secondhand.oauth.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.oauth.OauthAttributes;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberOauthRequest {

	private String nickname;
	private String email;
	private String profileImgUrl;
	private OauthAttributes type;

	@Builder
	private MemberOauthRequest(String nickname, String email, String profileImgUrl, String type) {
		this.nickname = nickname;
		this.email = email;
		this.profileImgUrl = profileImgUrl;
		this.type = OauthAttributes.validateOauthAttributes(type);
	}
}
