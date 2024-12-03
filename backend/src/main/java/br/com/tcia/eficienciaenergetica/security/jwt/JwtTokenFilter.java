package br.com.tcia.eficienciaenergetica.security.jwt;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends GenericFilterBean {

	@Autowired
	private JwtTokenService tokenProvider;
	
	public JwtTokenFilter(JwtTokenService tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		String token = tokenProvider.resolveToken((HttpServletRequest) request);
//		if (Objects.nonNull(token) && tokenProvider.validateToken(token)) {
//			Authentication auth = tokenProvider.getAuthentication(token);
//			if (Objects.nonNull(auth)) {
//				SecurityContextHolder.getContext().setAuthentication(auth);
//			}
//		}
//		chain.doFilter(request, response);		
	}
}