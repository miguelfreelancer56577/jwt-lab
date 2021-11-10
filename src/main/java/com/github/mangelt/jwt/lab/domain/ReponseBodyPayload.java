package com.github.mangelt.jwt.lab.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReponseBodyPayload<T> {
	Integer status;
	String message;
	T content;
	public ReponseBodyPayload(Integer status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public ReponseBodyPayload(Integer status) {
		super();
		this.status = status;
	}
}
