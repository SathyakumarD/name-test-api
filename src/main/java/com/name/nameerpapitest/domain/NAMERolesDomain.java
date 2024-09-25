package com.name.nameerpapitest.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "NAME_ROLES")
public class NAMERolesDomain {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Integer roleId;
	
	@ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "ROLE_NAME")
    private AppRole roleName;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "NAME_ROLE_USER",joinColumns = @JoinColumn(name="ROLE_ID"),inverseJoinColumns = @JoinColumn(name="USER_ID"))
    @JsonBackReference
    private List<NAMEUserDomain> users;
	
	public NAMERolesDomain(AppRole roleName) {
        this.roleName = roleName;
    }

}
