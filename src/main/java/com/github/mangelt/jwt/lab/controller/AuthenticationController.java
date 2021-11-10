package com.github.mangelt.jwt.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mangelt.jwt.lab.domain.ReponseBodyPayload;
import com.github.mangelt.jwt.lab.domain.RequestAuthenticationPayload;
import com.github.mangelt.jwt.lab.domain.ResponseAuthenticationPayload;
import com.github.mangelt.jwt.lab.service.AuthenticationService;
import com.github.mangelt.jwt.lab.util.ApiConstants;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = ApiConstants.API_VERSION + ApiConstants.MAPPING_AUTHENTICATION)
public class AuthenticationController {

	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping(path = ApiConstants.MAPPING_SIGNIN)
	public ResponseEntity<ReponseBodyPayload<ResponseAuthenticationPayload>> signIn(@RequestBody RequestAuthenticationPayload payload){
		return authenticationService.signIn(payload);
	}
	
}
