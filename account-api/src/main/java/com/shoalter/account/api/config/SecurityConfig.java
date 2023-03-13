package com.shoalter.account.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		security.cors();
		security.csrf().disable();
		security.headers().contentSecurityPolicy("script-src 'self'");
		security.headers().frameOptions().deny();
		security.authorizeRequests().anyRequest().permitAll();
		security.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return security.build();
	}
}
