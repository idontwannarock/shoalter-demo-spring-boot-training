package com.shoalter.account.api.config.security;

import com.shoalter.account.api.config.security.exception.InvalidTokenException;
import com.shoalter.account.api.config.security.exception.UnexpectedAuthenticationException;
import com.shoalter.account.api.config.security.pojo.AuthenticatedUser;
import com.shoalter.account.api.config.security.service.TokenUserService;
import com.shoalter.account.api.config.security.service.UserAuthorityService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
@Component
public class BearerTokenAuthenticationFilter extends OncePerRequestFilter {

	private static final String BEARER_PREFIX= "Bearer ";

	private final AuthenticationEntryPoint customAuthenticationEntryPoint;
	private final TokenUserService tokenUserService;
	private final UserAuthorityService userAuthorityService;

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.customAuthenticationEntryPoint, "A CustomAuthenticationEntryPoint is required");
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return isNotApi(request)
				|| isAuthApi(request)
				|| isAuthenticated()
				|| isNotContainBearerToken(request);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
			SecurityContextHolder.getContext().setAuthentication(asAuthentication(token));
			filterChain.doFilter(request, response);
		} catch (AuthenticationException e) {
			customAuthenticationEntryPoint.commence(request, response, e);
		}
	}

	private boolean isNotApi(HttpServletRequest request) {
		return !request.getServletPath().startsWith("/api/");
	}

	private boolean isAuthApi(HttpServletRequest request) {
		return request.getServletPath().startsWith("/api/auth/");
	}

	private boolean isAuthenticated() {
		return SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}

	/**
	 * token must be placed in Authorization header with Bearer scheme prefix
	 */
	private boolean isNotContainBearerToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		return !StringUtils.hasText(authorizationHeader)
				|| !authorizationHeader.startsWith(BEARER_PREFIX)
				|| !StringUtils.hasText(authorizationHeader.substring(7));
	}

	private Authentication asAuthentication(String token) {
		try {
			AuthenticatedUser user = tokenUserService.toUser(token);
			Collection<? extends GrantedAuthority> authorities = userAuthorityService.getAuthorities(user);
			return new TokenAuthenticatedUser(user, token, authorities);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
			throw new InvalidTokenException(String.format("Parse jwt claims error: %s", e.getMessage()));
		} catch (Exception e) {
			throw new UnexpectedAuthenticationException(e.getMessage());
		}
	}

	@EqualsAndHashCode(callSuper = true)
	private static class TokenAuthenticatedUser extends AbstractAuthenticationToken {

		private static final long serialVersionUID = -1887189041806242022L;

		private final AuthenticatedUser principal;

		private String credentials;

		private TokenAuthenticatedUser(AuthenticatedUser principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
			super(authorities);
			this.principal = principal;
			this.credentials = credentials;
			super.setAuthenticated(true); // must use super, as we override
		}

		@Override
		public String getCredentials() {
			return this.credentials;
		}

		@Override
		public AuthenticatedUser getPrincipal() {
			return this.principal;
		}

		@Override
		public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
			Assert.isTrue(!isAuthenticated,
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
			super.setAuthenticated(false);
		}

		@Override
		public void eraseCredentials() {
			super.eraseCredentials();
			this.credentials = null;
		}
	}
}
