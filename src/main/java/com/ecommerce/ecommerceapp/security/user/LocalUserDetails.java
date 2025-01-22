package com.ecommerce.ecommerceapp.security.user;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.ecommerceapp.datamodel.LocalUser;


public class LocalUserDetails implements UserDetails {
	private Long id;
	private String email;
	private String password;
    private Collection<GrantedAuthority> authorities;
	
	public LocalUserDetails() {
		super();
	}
	
	
	public LocalUserDetails(Long id, String email, String password, Collection<GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	
	public static LocalUserDetails buildUserDetails(LocalUser user) {
		Set<GrantedAuthority> userAuthorities = user.getRoles()
		    .stream()
		    .map(role-> new SimpleGrantedAuthority(role.getName()))
		    .collect(Collectors.toSet());
		return new LocalUserDetails(user.getId(), user.getEmail(), user.getPassword(), userAuthorities);
	}
	

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}


	@Override
	public String getPassword() {
		return password;
	}


	public String getEmail() {
		return email;
	}


	@Override
	public String getUsername() {
		return email;
	}
	
}
