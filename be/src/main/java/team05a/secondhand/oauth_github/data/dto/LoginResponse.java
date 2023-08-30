package team05a.secondhand.oauth_github.data.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.address.data.dto.AddressResponse;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {

	private MemberLoginResponse member;
	private TokenResponse tokens;
	private List<AddressResponse> addresses;

	public LoginResponse(MemberLoginResponse member, TokenResponse tokens, List<AddressResponse> address) {
		this.member = member;
		this.tokens = tokens;
		this.addresses = address;
	}
}
