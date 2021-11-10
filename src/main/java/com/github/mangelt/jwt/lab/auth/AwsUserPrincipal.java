package com.github.mangelt.jwt.lab.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.mangelt.jwt.lab.entity.DynamoUserEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AwsUserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 7571816734187601483L;
	
	@Transient
	private final transient DynamoUserEntity user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final Set<SimpleGrantedAuthority> grantedAuthorities;
		if(Objects.isNull(user.getAuthGroups())) {
			return Collections.emptySet();
		}
		grantedAuthorities = user
								.getAuthGroups()
								.stream()
								.map(SimpleGrantedAuthority::new)
								.collect(Collectors.toSet());
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.getIsAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getIsAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.getIsCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.getIsEnabled();
	}

}
