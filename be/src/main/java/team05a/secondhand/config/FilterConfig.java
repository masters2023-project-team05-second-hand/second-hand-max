package team05a.secondhand.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import team05a.secondhand.filter.CorsFilter;
import team05a.secondhand.filter.JwtAuthorizationFilter;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.redis.repository.RedisRepository;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<JwtAuthorizationFilter> jwtFilter(JwtTokenProvider provider,
		RedisRepository redisRepository) {
		FilterRegistrationBean<JwtAuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new JwtAuthorizationFilter(provider, redisRepository));
		filterRegistrationBean.setOrder(2);
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(@Value("${cors.origin}") String allowedOrigin) {
		FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new CorsFilter(allowedOrigin));
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}
}
