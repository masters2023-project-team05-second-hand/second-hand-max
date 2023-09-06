package team05a.secondhand.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import team05a.secondhand.member.resolver.LoginArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private LoginArgumentResolver loginArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginArgumentResolver);
	}
}
