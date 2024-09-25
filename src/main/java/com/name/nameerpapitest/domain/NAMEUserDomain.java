package com.name.nameerpapitest.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "NAME_USERS", uniqueConstraints = { @UniqueConstraint(columnNames = "USER_NAME"),
		@UniqueConstraint(columnNames = "EMAIL") })
public class NAMEUserDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userId;
	
	@NotBlank
    @Size(max = 20)
    @Column(name = "USER_NAME")
    private String userName;
	
	@NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "EMAIL")
    private String email;
	
	@Size(max = 120)
    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;
	
	@NotNull
	@Column(name = "ACCOUNT_NON_LOCKED")
	private boolean accountNonLocked = true;
	
	@NotNull
	@Column(name = "ACCOUNT_NON_EXPIRED")
    private boolean accountNonExpired = true;
	
	@NotNull
	@Column(name = "CREDENTIALS_NON_EXPIRED")
    private boolean credentialsNonExpired = true;
	
	@Column(name = "ENABLED")
    private boolean enabled = true;

	@Column(name = "CREDENTIALS_EXPIRY_DATE")
    private LocalDate credentialsExpiryDate;
	
	@Column(name = "ACCOUNT_EXPIRY_DATE")
    private LocalDate accountExpiryDate;

	@Column(name = "TWO_FACTOR_SECRET")
    private String twoFactorSecret;
	
	@Column(name = "IS_TWO_FACTOR_ENABLED")
    private boolean isTwoFactorEnabled = false;
	
	@Column(name = "SIGN_UP_METHOD")
    private String signUpMethod;
    
    @CreationTimestamp
    @Column(updatable = false,name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "NAME_ROLE_USER",joinColumns = @JoinColumn(name="USER_ID"),inverseJoinColumns = @JoinColumn(name="ROLE_ID"))
    //@JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @JsonBackReference
    private List<NAMERolesDomain> role;
    
    public NAMEUserDomain(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public NAMEUserDomain(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }


}
