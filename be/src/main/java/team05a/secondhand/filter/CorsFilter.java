package team05a.secondhand.filter;

import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:5173");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                "Authorization, x-requested-with, origin, content-type, accept");
        if (((HttpServletRequest) servletRequest).getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}
