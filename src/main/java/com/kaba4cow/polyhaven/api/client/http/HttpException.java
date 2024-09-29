package com.kaba4cow.polyhaven.api.client.http;

/**
 * Exception thrown when an HTTP request fails or returns an error response.
 * This class extends {@link Exception} and provides detailed information about
 * the HTTP response code and a custom error message.
 */
public class HttpException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code HttpException} with a formatted error message
	 * containing the HTTP response code and the provided message.
	 *
	 * @param response the {@link HttpResponse} object containing the HTTP response
	 *                 code
	 * @param message  a descriptive error message providing additional context for
	 *                 the exception
	 */
	public HttpException(HttpResponse response, String message) {
		super(String.format("Error %d: %s", response.getCode(), message));
	}

}
