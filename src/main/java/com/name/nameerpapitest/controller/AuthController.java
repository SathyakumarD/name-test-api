package com.name.nameerpapitest.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.name.nameerpapitest.securityconfig.dtos.LoginRequest;
import com.name.nameerpapitest.securityconfig.dtos.LoginResponse;
import com.name.nameerpapitest.securityconfig.dtos.MessageResponse;
import com.name.nameerpapitest.securityconfig.dtos.SignupRequest;
import com.name.nameerpapitest.securityconfig.jwt.JwtUtils;
import com.name.nameerpapitest.services.SignInService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    SignInService signInService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        //      set the authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // specific to our implemetation
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        // Collect roles from the UserDetails
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Prepare the response body, now including the JWT token directly in the body
        LoginResponse response = new LoginResponse(userDetails.getUsername(),
                roles, jwtToken);

        // Return the response entity with the JWT token included in the response body
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/signup")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws Exception {
    	String response = signInService.registerUser(signUpRequest);
    	if(response!=null && response.contains("Error")) {
    		return ResponseEntity.badRequest().body(new MessageResponse(response)); 
    	}else {
    		return ResponseEntity.ok(new MessageResponse(response));
    	}
    }
}
