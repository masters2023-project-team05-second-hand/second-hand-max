package team05a.secondhand.oauth_github.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.member.data.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginResponse {

	private String nickname;
	private String profileImgUrl;

	public MemberLoginResponse(String nickname, String profileImgUrl) {
		this.nickname = nickname;
		this.profileImgUrl = profileImgUrl;
	}

	public static MemberLoginResponse from(Member member) {
		return new MemberLoginResponse(member.getNickname(), member.getProfileImgUrl());
	}
}
