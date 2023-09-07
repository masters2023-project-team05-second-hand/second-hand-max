package team05a.secondhand.member.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import team05a.secondhand.jwt.JwtTokenProvider;

@RequiredArgsConstructor
@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(MemberId.class) && parameter.getParameterType().equals(Long.class);
	}

	@Override
	public Long resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		if (webRequest.getHeader(HttpHeaders.AUTHORIZATION) != null) {
			String accessToken = webRequest.getHeader(HttpHeaders.AUTHORIZATION).split("Bearer ")[1];
			return jwtTokenProvider.extractMemberId(accessToken);
		}
		return -1L;
	}
}
