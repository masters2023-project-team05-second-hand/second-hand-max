package team05a.secondhand.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.jwt.filter.JwtAuthorizationFilter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<JwtAuthorizationFilter> jwtFilter(JwtTokenProvider provider, ObjectMapper mapper) {
		FilterRegistrationBean<JwtAuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new JwtAuthorizationFilter(provider, mapper));
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}
}
