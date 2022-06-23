package com.hoanghiep.perfume.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.hoanghiep.perfume.exception.JwtAuthenticationException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean{
	
	private final JwtFactory jwtFactory;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String token = jwtFactory.resolveToken((HttpServletRequest) request);

        try {
            if (token != null && jwtFactory.validateToken(token)) {
                Authentication authentication = jwtFactory.getAuthentication(token);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) response).sendError(e.getStatus().value());
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
        chain.doFilter(request, response);
    
	}
	
}
