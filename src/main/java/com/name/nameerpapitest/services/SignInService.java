package com.name.nameerpapitest.services;

import com.name.nameerpapitest.securityconfig.dtos.SignupRequest;

public interface SignInService {
	
	public String registerUser(SignupRequest signUpRequest) throws Exception;

}
