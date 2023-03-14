package com.shoalter.account.api.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

	private static final String[] AUTH_WHITELIST = {
			"/**/swagger-resources/**",
			"/**/swagger-ui/**",
			"/**/v3/api-docs/**",
			"/**/v3/api-docs",
			"/**/webjars/**",
			"/**/error",
			"/**/actuator/**",
	};

	private final BearerTokenAuthenticationFilter bearerTokenAuthenticationFilter;
	private final AuthenticationEntryPoint customAuthenticationEntryPoint;

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
		security.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		security.formLogin().disable().logout().disable();
		security.httpBasic().disable();
		security.anonymous().disable();
		security.addFilterBefore(bearerTokenAuthenticationFilter, DigestAuthenticationFilter.class);
		security.authorizeHttpRequests(registry -> registry
				.antMatchers(AUTH_WHITELIST).permitAll()
				.antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
				.anyRequest().authenticated());
		security.exceptionHandling()
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedHandler(new CustomAccessDeniedHandler());
		return security.build();
	}
}
