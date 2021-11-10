package com.github.mangelt.jwt.lab.component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.mangelt.jwt.lab.exception.AppException;
import com.github.mangelt.jwt.lab.util.ApiConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtComponent {
	
	@Value("${app.config.security.secret.key}")
	private String secretKey;
	@Value("${app.config.security.id}")
	private String securityId;
	@Value("${app.config.security.expiration.time}")
	private Integer expirationTime;
	@Value("${app.config.security.refresh.expiration.time}")
	private Integer refreshExpirationTime;
	
	public String createToken(@NonNull UserDetails userDetails) {
		return createToken(userDetails, expirationTime);
	}
	
	public String refreshToken(@NonNull UserDetails userDetails) {
		return createToken(userDetails, refreshExpirationTime);
	}
	
	private String createToken(@NonNull UserDetails userDetails,@NonNull Integer expiration) {
		return Jwts
			.builder()
			.setId(securityId)
			.setSubject(userDetails.getUsername())
			.claim(ApiConstants.CARD_AUTHORITIES,
					userDetails.getAuthorities().stream()
							.map(GrantedAuthority::getAuthority)
							.collect(Collectors.toList()))
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiration))
			.signWith(SignatureAlgorithm.HS512,
					secretKey.getBytes()).compact();
	}
	
	public Claims getClaims(final String token) {
		Claims claims;
		try {
			claims = Jwts.parser().requireId(securityId).setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			log.error(ApiConstants.EXP_ERROR_INVALID_CREDENCIALS, ex);
			throw new AppException(ApiConstants.EXP_ERROR_INVALID_CREDENCIALS, ex);
		} catch (ExpiredJwtException ex) {
			log.error(ApiConstants.EXP_ERROR_EXPIRED_TOKEN, ex);
			throw new AppException(ApiConstants.EXP_ERROR_EXPIRED_TOKEN, ex);
		}
		return claims;
	}
	
	public String getUserName(final Claims claims) {
		Optional<String> optSubject = Optional.ofNullable(claims.getSubject());
		return optSubject.orElseThrow(()->new AppException(ApiConstants.EXP_ERROR_INVALID_USER, null));
	}
	
	public List<SimpleGrantedAuthority> getRoles(final Claims claims) {
		@SuppressWarnings("unchecked")
		final Optional<List<String>> optAuthorities = Optional.ofNullable((List<String>)claims.get(ApiConstants.CARD_AUTHORITIES));
		final List<SimpleGrantedAuthority> lstGrantedAuthorities;
		if(!optAuthorities.isPresent()) {
			log.error(ApiConstants.EXP_ERROR_INVALID_ROLES);
			throw new AppException(ApiConstants.EXP_ERROR_INVALID_ROLES, null);
		}else {
			lstGrantedAuthorities = optAuthorities
					.get()
					.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		}
		return lstGrantedAuthorities;
	}
	
	public Optional<String> extractTokenFromRequest(HttpServletRequest request) {
		final String prefixToken = ApiConstants.CARD_BEARER.concat(ApiConstants.CARD_BLANK);
		String bearerToken = request.getHeader(ApiConstants.CARD_AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(prefixToken)) {
			return Optional.of(bearerToken.replace(prefixToken, ApiConstants.CARD_EMPTY));
		}else {
			return Optional.ofNullable(null);
		}
	}
}
