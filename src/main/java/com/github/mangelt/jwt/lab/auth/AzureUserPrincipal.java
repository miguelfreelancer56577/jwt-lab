package com.github.mangelt.jwt.lab.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.github.mangelt.jwt.lab.entity.TableStorageUser;

public class AzureUserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 5899800164379723163L;
	
	private final transient TableStorageUser user;
	
	public AzureUserPrincipal(TableStorageUser user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final Set<SimpleGrantedAuthority> grantedAuthorities;
		if(Objects.isNull(user.getAuthGroups())) {
			return Collections.emptySet();
		}
		grantedAuthorities = Stream.of(StringUtils.commaDelimitedListToStringArray(user.getAuthGroups()))
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
		return user.getPartitionKey();
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
