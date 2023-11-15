package com.sha.insta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sha.insta.payload.JWTAuthResponse;
import com.sha.insta.payload.LoginDto;
import com.sha.insta.payload.RegisterDto;
import com.sha.insta.service.impl.AuthService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
	
	
	private AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping(value= {"/login", "/signin"})
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
		
		
		String token = authService.login(loginDto);
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		
		jwtAuthResponse.setAccessToken(token);
		
		return ResponseEntity.ok(jwtAuthResponse);
		
		
	}
	@PostMapping(value= {"/register", "/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		
		log.info(registerDto.toString());
		
		String response = authService.register(registerDto);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
		
		
	}

}
