package com.name.nameerpapitest.services.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.name.nameerpapitest.domain.AppRole;
import com.name.nameerpapitest.domain.NAMERolesDomain;
import com.name.nameerpapitest.domain.NAMEUserDomain;
import com.name.nameerpapitest.dtos.UserDTO;
import com.name.nameerpapitest.repository.RoleRepository;
import com.name.nameerpapitest.repository.UserRepository;
import com.name.nameerpapitest.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void updateUserRole(Long userId, String roleName) {
    	NAMEUserDomain user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        AppRole appRole = AppRole.valueOf(roleName);
        NAMERolesDomain role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRole().add(role);
        //user.setRole(role);
        userRepository.save(user);
    }


    @Override
    public List<NAMEUserDomain> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public UserDTO getUserById(Long id) {
//        return userRepository.findById(id).orElseThrow();
    	NAMEUserDomain user = userRepository.findById(id).orElseThrow();
        return convertToDto(user);
    }

    private UserDTO convertToDto(NAMEUserDomain user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.getCredentialsExpiryDate(),
                user.getAccountExpiryDate(),
                user.getTwoFactorSecret(),
                user.isTwoFactorEnabled(),
                user.getSignUpMethod(),
                user.getRole(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }


}
