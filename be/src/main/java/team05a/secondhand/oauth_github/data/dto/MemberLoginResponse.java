package team05a.secondhand.oauth_github.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.member.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginResponse {

	@JsonIgnore
	private Long id;
	private String nickname;
	private String profileImgUrl;

	public MemberLoginResponse(Long id, String nickname, String profileImgUrl) {
		this.id = id;
		this.nickname = nickname;
		this.profileImgUrl = profileImgUrl;
	}

	public static MemberLoginResponse from(Member member) {
		return new MemberLoginResponse(member.getId(), member.getNickname(), member.getProfileImgUrl());
	}
}
