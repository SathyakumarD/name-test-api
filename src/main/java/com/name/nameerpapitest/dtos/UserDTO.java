package com.name.nameerpapitest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.name.nameerpapitest.domain.NAMERolesDomain;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private String email;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;
    private String twoFactorSecret;
    private boolean isTwoFactorEnabled;
    private String signUpMethod;
    private List<NAMERolesDomain> role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
