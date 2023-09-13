package team05a.secondhand.member.data.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberNicknameUpdateRequest {

	@NotBlank(message = "닉네임은 필수입니다.")
	private String newNickname;

	@Builder
	private MemberNicknameUpdateRequest(String newNickname) {
		this.newNickname = newNickname;
	}
}
