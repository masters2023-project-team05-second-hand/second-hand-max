package team05a.secondhand.oauth.data.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.address.data.dto.AddressResponse;
import team05a.secondhand.jwt.Jwt;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {

	private MemberLoginResponse member;
	private Jwt tokens;
	private List<AddressResponse> addresses;

	public LoginResponse(MemberLoginResponse member, Jwt tokens, List<AddressResponse> address) {
		this.member = member;
		this.tokens = tokens;
		this.addresses = address;
	}

	public String createAuthorizationHeader() {
		return "Bearer " + tokens.getAccessToken();
	}
}
