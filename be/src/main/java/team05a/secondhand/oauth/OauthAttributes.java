package team05a.secondhand.oauth;

import java.util.Arrays;
import java.util.Map;

import lombok.Getter;
import team05a.secondhand.errors.errorcode.OauthErrorCode;
import team05a.secondhand.errors.exception.IllegalOauthAttributesException;
import team05a.secondhand.oauth.data.dto.MemberOauthRequest;

@Getter
public enum OauthAttributes {

	GITHUB("github") {
		@Override
		public MemberOauthRequest of(String providerName, Map<String, Object> attributes) {
			return new MemberOauthRequest((String)attributes.get("login"), (String)attributes.get("email"),
				(String)attributes.get("avatar_url"), providerName);
		}
	},
	KAKAO("kakao") {
		@Override
		public MemberOauthRequest of(String providerName, Map<String, Object> attributes) {
			Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
			Map<String, Object> profile = (Map<String, Object>)kakaoAccount.get("profile");

			return new MemberOauthRequest((String)profile.get("nickname"), (String)kakaoAccount.get("email"),
				(String)profile.get("profile_image_url"), providerName);
		}
	};

	private final String providerName;

	OauthAttributes(String name) {
		this.providerName = name;
	}

	public static MemberOauthRequest extract(String providerName, Map<String, Object> attributes) {
		return Arrays.stream(values())
			.filter(provider -> providerName.equals(provider.providerName))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new)
			.of(providerName, attributes);
	}

	public static OauthAttributes validateOauthAttributes(String type) {
		try {
			return OauthAttributes.valueOf(type.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalOauthAttributesException(OauthErrorCode.INVALID_OAUTH_ATTRIBUTES);
		}
	}

	public abstract MemberOauthRequest of(String providerName, Map<String, Object> attributes);
}
