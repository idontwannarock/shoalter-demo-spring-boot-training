package com.shoalter.account.api.config.security.service;

import com.shoalter.account.api.config.security.pojo.AuthenticatedUser;
import com.shoalter.account.api.exception.InvalidRefreshTokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUserService {

	private static final String USER_ID_KEY = "userId";
	private static final String USERNAME_KEY = "username";

	@Value("${jwt.access-token.sign.key}")
	private String accessTokenSignKey;

	@Value("${jwt.access-token.expiration}")
	private Long accessTokenExpiration;

	@Value("${jwt.refresh-token.sign.key}")
	private String refreshTokenSignKey;

	@Value("${jwt.refresh-token.expiration}")
	private Long refreshTokenExpiration;

	public AuthenticatedUser toUser(String token) {
		Claims claims = Jwts.parser().setSigningKey(accessTokenSignKey).parseClaimsJws(token).getBody();
		return AuthenticatedUser.builder()
				.userId(claims.get(USER_ID_KEY, Integer.class))
				.username(claims.get(USERNAME_KEY, String.class))
				.build();
	}

	public String toAccessToken(Integer userId, String username) {
		return Jwts.builder()
				.claim(USER_ID_KEY, userId)
				.claim(USERNAME_KEY, username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
				.signWith(SignatureAlgorithm.HS256, accessTokenSignKey)
				.compact();
	}

	public String toRefreshToken(Integer userId) {
		return Jwts.builder()
				.claim(USER_ID_KEY, userId)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
				.signWith(SignatureAlgorithm.HS256, refreshTokenSignKey)
				.compact();
	}

	public void isRefreshTokenValid(String refreshToken, Integer userId) {
		Claims claims = Jwts.parser().setSigningKey(refreshTokenSignKey).parseClaimsJws(refreshToken).getBody();
		Integer userIdInToken = claims.get(USER_ID_KEY, Integer.class);
		if (userIdInToken == null || !userIdInToken.equals(userId)) {
			throw new InvalidRefreshTokenException("Invalid user id in token");
		}
	}
}
