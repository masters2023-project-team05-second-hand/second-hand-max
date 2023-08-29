package team05a.secondhand.oauth_github.data.dto;

import java.util.List;

public class LoginResponse {

	private final TokenResponse token;
	private final List<AddressResponse> address;

	public LoginResponse(TokenResponse token, List<AddressResponse> address) {
		this.token = token;
		this.address = address;
	}
}
