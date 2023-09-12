package team05a.secondhand.filter;

import static team05a.secondhand.oauth.service.OauthService.*;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import com.fasterxml.jackson.core.JsonParseException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.errors.exception.AuthenticationHeaderNotFoundException;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member_refreshtoken.repository.MemberRefreshTokenRepository;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements Filter {

	private final String[] whiteListGetUris = new String[] {"/api/addresses", "/api/categories", "/api/products/*"};
	private final JwtTokenProvider jwtProvider;
	private final MemberRefreshTokenRepository memberRefreshTokenRepository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;

		if (whiteListGetCheck(httpServletRequest) || whiteListLoginCheck(httpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}

		if (!isContainToken(httpServletRequest)) {
			throw new AuthenticationHeaderNotFoundException();
		}

		try {
			String accessToken = jwtProvider.getToken(httpServletRequest);
			jwtProvider.extractMemberId(accessToken);
			if (memberRefreshTokenRepository.get(ACCESS_TOKEN_PREFIX + accessToken).isPresent()) {
				log.warn("AccessTokenBlacklistException");
				httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "블랙리스트에 등록된 액세스 토큰으로는 접근할 수 없습니다.");
				return;
			}
			chain.doFilter(request, response);
		} catch (JsonParseException e) {
			log.error("JsonParseException");
			httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value());
		} catch (MalformedJwtException | UnsupportedJwtException e) {
			log.error("JwtException");
			httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "인증 오류");
		} catch (ExpiredJwtException e) {
			log.error("JwtTokenExpired");
			httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "토큰이 만료 되었습니다");
		}
	}

	private boolean whiteListGetCheck(HttpServletRequest httpServletRequest) {
		String uri = httpServletRequest.getRequestURI();
		return httpServletRequest.getMethod().equals("GET") && PatternMatchUtils.simpleMatch(whiteListGetUris, uri);
	}

	private boolean whiteListLoginCheck(HttpServletRequest httpServletRequest) {
		String uri = httpServletRequest.getRequestURI();
		String whiteListLoginUris = "/api/members/sign-in/*";
		return httpServletRequest.getMethod().equals("POST") && PatternMatchUtils.simpleMatch(whiteListLoginUris, uri);
	}

	private boolean isContainToken(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		return authorization != null && authorization.startsWith("Bearer ");
	}
}
