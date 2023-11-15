package com.sha.insta.service;

import com.sha.insta.payload.LoginDto;
import com.sha.insta.payload.RegisterDto;

public interface IAuthService {
	
	String login(LoginDto loginDto);
	
	String register(RegisterDto registerDto);

}
