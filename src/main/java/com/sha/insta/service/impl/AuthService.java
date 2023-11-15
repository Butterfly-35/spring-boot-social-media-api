package com.sha.insta.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sha.insta.entity.Role;
import com.sha.insta.entity.User;
import com.sha.insta.exception.BlogAPIException;
import com.sha.insta.payload.LoginDto;
import com.sha.insta.payload.RegisterDto;
import com.sha.insta.repository.RoleRepository;
import com.sha.insta.repository.UserRepository;
import com.sha.insta.security.JwtTokenProvider;
import com.sha.insta.service.IAuthService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService implements IAuthService{
	
	private AuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	private RoleRepository roleRepository;
	private UserRepository userRepository;
	
	
	

	public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
			JwtTokenProvider jwtTokenProvider, RoleRepository roleRepository, UserRepository userRepository) {
		super();
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	public String login(LoginDto loginDto) {
		// TODO Auto-generated method stub
		
		log.info(loginDto.toString());
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		return token;
	}

	public String register(RegisterDto registerDto) {
		// TODO Auto-generated method stub
		
		if(userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
		}
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
		}
		
		log.info(registerDto.toString());
		
		User user = new User();
		
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ADMIN").get();
		
		log.info(userRole.toString());
		roles.add(userRole);
		log.info(user.toString());
		user.setRoles(roles);
		userRepository.save(user);
		
		return "User registered successfully";
		
		
	}

}
