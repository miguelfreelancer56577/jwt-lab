package com.github.mangelt.jwt.lab.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.mangelt.jwt.lab.component.JwtComponent;
import com.github.mangelt.jwt.lab.util.ApiConstants;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticatorFilter extends OncePerRequestFilter{

	private final JwtComponent jwtComponent;
	
	public JwtAuthenticatorFilter(JwtComponent jwtComponent) {
		this.jwtComponent = jwtComponent;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final Optional<String> optToken = jwtComponent.extractTokenFromRequest(request);
		final UserDetails userDetails;
		final Claims claims;
		final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
		if(optToken.isPresent()) {
			claims = jwtComponent.getClaims(optToken.get());
			userDetails = new User(jwtComponent.getUserName(claims), ApiConstants.CARD_EMPTY, jwtComponent.getRoles(claims));
			usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}else {
			log.info(ApiConstants.EXP_ERROR_REQUEST_WITHOUT_TOKEN);
		}
		filterChain.doFilter(request, response);
	}

}
