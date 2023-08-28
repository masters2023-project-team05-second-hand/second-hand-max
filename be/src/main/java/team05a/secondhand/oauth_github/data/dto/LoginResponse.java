package team05a.secondhand.oauth_github.data.dto;

import java.util.List;

public class LoginResponse {

	private final TokenResponse token;
	private final List<AddressResponse> address;
	private final Long lastVisitedAddressId;

	public LoginResponse(TokenResponse token, List<AddressResponse> address, Long lastVisitedAddressId) {
		this.token = token;
		this.address = address;
		this.lastVisitedAddressId = lastVisitedAddressId;
	}
}
