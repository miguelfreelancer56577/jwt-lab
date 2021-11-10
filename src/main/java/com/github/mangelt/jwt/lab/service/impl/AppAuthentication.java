package com.github.mangelt.jwt.lab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.github.mangelt.jwt.lab.component.JwtComponent;
import com.github.mangelt.jwt.lab.domain.ReponseBodyPayload;
import com.github.mangelt.jwt.lab.domain.RequestAuthenticationPayload;
import com.github.mangelt.jwt.lab.domain.ResponseAuthenticationPayload;
import com.github.mangelt.jwt.lab.exception.AppException;
import com.github.mangelt.jwt.lab.service.AuthenticationService;
import com.github.mangelt.jwt.lab.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppAuthentication implements AuthenticationService{

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	JwtComponent jwtComponent;
	
	@Override
	public ResponseEntity<ReponseBodyPayload<ResponseAuthenticationPayload>> signIn(RequestAuthenticationPayload payload) {
		final ReponseBodyPayload<ResponseAuthenticationPayload> response = new ReponseBodyPayload<>(HttpStatus.OK.value(), ApiConstants.USER_SERVICE_SIGNIN_OK);
		final UserDetails userDetails;
		final UsernamePasswordAuthenticationToken usernamePasswordCredentials = new UsernamePasswordAuthenticationToken(payload.getUserId(), payload.getPassword());
		try {
			authenticationManager.authenticate(usernamePasswordCredentials);
		} catch (DisabledException ex) {
			log.error(ApiConstants.EXP_ERROR_DISABLED_USER, ex);
			throw new AppException(ApiConstants.EXP_ERROR_DISABLED_USER, ex);
		}
		catch (BadCredentialsException ex) {
			log.error(ApiConstants.EXP_ERROR_INVALID_CREDENCIALS, ex);
			throw new AppException(ApiConstants.EXP_ERROR_INVALID_CREDENCIALS, ex);
		}
		userDetails = userDetailsService.loadUserByUsername(payload.getUserId());
		response.setContent(
				ResponseAuthenticationPayload
					.builder()
					.token(jwtComponent
							.createToken(userDetails))
					.build());
		return ResponseEntity.ok(response);
	}

}
