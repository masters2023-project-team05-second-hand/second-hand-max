package team05a.secondhand.oauth_github.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthAccessCodeRequest {

	private String accessCode;
}
