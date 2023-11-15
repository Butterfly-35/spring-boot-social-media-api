package com.sha.insta.security;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sha.insta.entity.User;
import com.sha.insta.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private UserRepository userRepository;
	

	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}




	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not foun with username or email "+usernameOrEmail)));
		
		User user = null;
		if(optionalUser.isPresent()) {
			user = optionalUser.get();
		}
		
		Set<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		
		
		
		
				
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(),
				authorities);
	}

}
