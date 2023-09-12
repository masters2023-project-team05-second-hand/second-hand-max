package team05a.secondhand.member.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.member.data.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

	private Long id;
	private String nickname;
	private String profileImgUrl;

	@Builder
	private MemberResponse(Long id, String nickname, String profileImgUrl) {
		this.id = id;
		this.nickname = nickname;
		this.profileImgUrl = profileImgUrl;
	}

	public static MemberResponse from(Member member) {
		return MemberResponse.builder()
			.id(member.getId())
			.nickname(member.getNickname())
			.profileImgUrl(member.getProfileImgUrl())
			.build();
	}
}
