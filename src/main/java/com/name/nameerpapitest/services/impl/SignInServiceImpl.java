package com.name.nameerpapitest.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.name.nameerpapitest.domain.AppRole;
import com.name.nameerpapitest.domain.NAMERolesDomain;
import com.name.nameerpapitest.domain.NAMEUserDomain;
import com.name.nameerpapitest.repository.RoleRepository;
import com.name.nameerpapitest.repository.UserRepository;
import com.name.nameerpapitest.securityconfig.dtos.SignupRequest;
import com.name.nameerpapitest.services.SignInService;

@Service
public class SignInServiceImpl implements SignInService {
	
	@Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;
    
    public String registerUser(SignupRequest signUpRequest) throws Exception{
    	if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            return "Error: Username is already taken!";
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return "Error: Email is already in use!";
        }

        // Create new user's account
        NAMEUserDomain user = new NAMEUserDomain(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        NAMERolesDomain role;

        if (strRoles == null || strRoles.isEmpty()) {
            role = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        } else {
            String roleStr = strRoles.iterator().next();
            //testing phase so it is in enum, in production grade need to change
            if (roleStr.equals("admin")) {
                role = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            } else {
                role = roleRepository.findByRoleName(AppRole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            }

            user.setAccountNonLocked(true);
            user.setAccountNonExpired(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            user.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
            user.setAccountExpiryDate(LocalDate.now().plusYears(1));
            user.setTwoFactorEnabled(false);
            user.setSignUpMethod("email");
        }
        user.setRole(List.of(role));
        userRepository.save(user);

        return "User registered successfully!";
    }

}
