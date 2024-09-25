package com.name.nameerpapitest.services;



import java.util.List;

import com.name.nameerpapitest.domain.NAMEUserDomain;
import com.name.nameerpapitest.dtos.UserDTO;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<NAMEUserDomain> getAllUsers();

    UserDTO getUserById(Long id);
}
