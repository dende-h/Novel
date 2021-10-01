package io.post.novel.auth;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserForm implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
    private long id;
	private String penName;
	private String password;
	private List<String> roles;
	private boolean locked;
	private boolean expired;



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return roles.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return penName;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return !expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return !expired;
	}

	@Override
	public boolean isEnabled() {
		
		return !locked;
	}

}
