package com.name.nameerpapitest.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.name.nameerpapitest.domain.NAMEUserDomain;
import com.name.nameerpapitest.dtos.UserDTO;
import com.name.nameerpapitest.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/getusers")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<NAMEUserDomain>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),
                HttpStatus.OK);
    }

    @PutMapping("/update-role")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> updateUserRole(@RequestParam Long userId, 
                                                 @RequestParam String roleName) {
        userService.updateUserRole(userId, roleName);
        return ResponseEntity.ok("User role updated");
    }

    @GetMapping("/user/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id),
                HttpStatus.OK);
    }


}
