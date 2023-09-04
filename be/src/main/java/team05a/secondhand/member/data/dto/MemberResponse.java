package team05a.secondhand.member.data.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.member.data.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private String nickname;
    private String profileImgUrl;

    public MemberResponse(String nickname, String profileImgUrl) {
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getNickname(), member.getProfileImgUrl());
    }
}
