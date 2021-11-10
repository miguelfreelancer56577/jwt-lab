package com.github.mangelt.jwt.lab.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.github.mangelt.jwt.lab.domain.ReponseBodyPayload;
import com.github.mangelt.jwt.lab.domain.RequestAuthenticationPayload;
import com.github.mangelt.jwt.lab.domain.ResponseAuthenticationPayload;

@Validated
public interface AuthenticationService {
	ResponseEntity<ReponseBodyPayload<ResponseAuthenticationPayload>> signIn(@Valid RequestAuthenticationPayload payload);
}
