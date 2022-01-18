package edu.aau.groupc.canteenbackend.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CanteenCorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // add CORS headers to response
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "localhost");
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Set-Cookie");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}