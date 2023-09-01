package team05a.secondhand.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team05a.secondhand.filter.CorsFilter;
import team05a.secondhand.filter.JwtAuthorizationFilter;
import team05a.secondhand.jwt.JwtTokenProvider;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthorizationFilter> jwtFilter(JwtTokenProvider provider, ObjectMapper mapper) {
        FilterRegistrationBean<JwtAuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JwtAuthorizationFilter(provider, mapper));
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CorsFilter());
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
