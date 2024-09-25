package com.name.nameerpapitest.securityconfig;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.name.nameerpapitest.domain.AppRole;
import com.name.nameerpapitest.domain.NAMERolesDomain;
import com.name.nameerpapitest.domain.NAMEUserDomain;
import com.name.nameerpapitest.repository.RoleRepository;
import com.name.nameerpapitest.repository.UserRepository;
import com.name.nameerpapitest.securityconfig.jwt.AuthEntryPointJwt;
import com.name.nameerpapitest.securityconfig.jwt.AuthTokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
securedEnabled = true)
public class SecurityConfig {
	
	@Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests)->requests
				.requestMatchers("/csrf-token").permitAll()
				.requestMatchers("/signin").permitAll()
				.anyRequest().authenticated()
				);
		//http.csrf((csrf)->csrf.disable());
		http.csrf(csrf ->
        csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.ignoringRequestMatchers("/api/auth/public/**")
				);
		//http.formLogin(withDefaults());
		http.exceptionHandling(exception
                -> exception.authenticationEntryPoint(unauthorizedHandler));
        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
		//http.httpBasic(Customizer.withDefaults());//enable basic auth
		return http.build();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean //for spring to autowire in AuthController
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
    public CommandLineRunner initData(RoleRepository roleRepository,
                                      UserRepository userRepository,PasswordEncoder passwordEncoder) {
        return args -> {
        	//NAMERolesDomain userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
            //        .orElseGet(() -> roleRepository.save());

        	//NAMERolesDomain adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
            //        .orElseGet(() -> roleRepository.save());
        	List<NAMERolesDomain> adminRoleList = Arrays.asList(new NAMERolesDomain(AppRole.ROLE_USER));

        	List<NAMERolesDomain> normaladminRoleList = Arrays.asList(new NAMERolesDomain(AppRole.ROLE_ADMIN));

            if (!userRepository.existsByUserName("sathya")) {
            	NAMEUserDomain admin = new NAMEUserDomain("sathya", "sathya@name.com", passwordEncoder.encode("123"));
                admin.setAccountNonLocked(true);
                admin.setAccountNonExpired(true);
                admin.setCredentialsNonExpired(true);
                admin.setEnabled(true);
                admin.setCredentialsExpiryDate(LocalDate.now().plusYears(10));
                admin.setAccountExpiryDate(LocalDate.now().plusYears(10));
                admin.setTwoFactorEnabled(false);
                admin.setSignUpMethod("email");
                admin.setRole(adminRoleList);
                userRepository.save(admin);
            }
            
            if (!userRepository.existsByUserName("bergin")) {
            	NAMEUserDomain admin = new NAMEUserDomain("bergin", "bergin@name.com", passwordEncoder.encode("123"));
                admin.setAccountNonLocked(true);
                admin.setAccountNonExpired(true);
                admin.setCredentialsNonExpired(true);
                admin.setEnabled(true);
                admin.setCredentialsExpiryDate(LocalDate.now().plusYears(10));
                admin.setAccountExpiryDate(LocalDate.now().plusYears(10));
                admin.setTwoFactorEnabled(false);
                admin.setSignUpMethod("email");
                admin.setRole(normaladminRoleList);
                userRepository.save(admin);
            }
        };
    }

}
