package com.shoalter.account.api.controller;

import com.shoalter.account.api.controller.dto.response.Response;
import com.shoalter.account.api.exception.ClientErrorException;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers {

	@ApiResponse(
			responseCode = "400",
			description = "1. Unsupported media type\n2. Missing request parameter\n3. Broken input message\n4. Unsupported request method",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({
			HttpMediaTypeNotSupportedException.class,
			MissingServletRequestParameterException.class,
			HttpMessageNotReadableException.class,
			HttpRequestMethodNotSupportedException.class,
			IllegalArgumentException.class,
			MethodArgumentNotValidException.class,
			ClientErrorException.class
	})
	public final Response<Void> handleBadRequest(Exception e) {
		return Response.warn(e.getMessage());
	}

	/**
	 * Handle intentionally threw AuthenticationException outside security filter chain
	 */
	@ApiResponse(
			responseCode = "401",
			description = "Unauthenticated",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler({
			AuthenticationException.class,
			JwtException.class
	})
	public final Response<Void> handleUnauthenticated(Exception e) {
		return Response.warn("Unauthenticated: " + e.getMessage());
	}

	/**
	 * Handle intentionally threw AccessDeniedException outside security filter chain
	 */
	@ApiResponse(
			responseCode = "403",
			description = "Unauthorized",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public final Response<Void> handleUnauthorizedAccess(AccessDeniedException e) {
		return Response.warn(e.getMessage());
	}

	@ApiResponse(
			responseCode = "500",
			description = "Unexpected server error",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Response<Void> serverError(Exception e) {
		log.error(e.getMessage(), e);
		return Response.error(e.getMessage());
	}
}
