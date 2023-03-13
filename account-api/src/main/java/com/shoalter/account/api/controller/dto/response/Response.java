package com.shoalter.account.api.controller.dto.response;

import lombok.Value;

import java.io.Serializable;

@Value
public class Response<T> implements Serializable {

	private static final long serialVersionUID = -7329010029344584918L;

	String status;
	String message;
	T data;

	public static <T> Response<T> success(T data) {
		return new Response<>("success", null, data);
	}

	public static Response<Void> warn(String message) {
		return new Response<>("warn", message, null);
	}

	public static Response<Void> error(String message) {
		return new Response<>("error", message, null);
	}

	private Response(String status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
}
