package team05a.secondhand.chat.data.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MessageCreateRequest {

	@NotNull(message = "메세지를 보내는 사람의 아이디는 필수입니다.")
	private Long senderId;
	@NotBlank(message = "메세지에는 빈 값일 수 없고 공백문자만으로 이루어질 수 없습니다.")
	private String content;

	@Builder
	private MessageCreateRequest(Long senderId, String content) {
		this.senderId = senderId;
		this.content = content;
	}
}
