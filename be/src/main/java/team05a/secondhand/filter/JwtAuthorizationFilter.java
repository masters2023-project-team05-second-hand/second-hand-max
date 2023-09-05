package team05a.secondhand.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.jwt.JwtTokenProvider;

@Slf4j
public class JwtAuthorizationFilter implements Filter {

	private final String[] whiteListGetUris = new String[] {"/api/addresses", "/api/categories", "/api/products/*"};
	private final JwtTokenProvider jwtProvider;
	private final ObjectMapper objectMapper;

	public JwtAuthorizationFilter(JwtTokenProvider jwtProvider, ObjectMapper objectMapper) {
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
	}

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
			httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "인증 오류");
			return;
		}
		try {
			String token = getToken(httpServletRequest);
			getId(token);
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
		String authorization = request.getHeader("Authorization");
		return authorization != null && authorization.startsWith("Bearer ");
	}

	private String getToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		return authorization.substring(7);
	}

	private void getId(String token) throws JsonProcessingException {
		Claims claims = jwtProvider.getClaims(token);
		Long id = claims.get("memberId", Long.class);
	}
}
