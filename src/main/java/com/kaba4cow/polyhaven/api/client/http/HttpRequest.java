package com.kaba4cow.polyhaven.api.client.http;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents an HTTP request with the ability to add parameters and format the
 * request as a query string.
 * 
 * <p>
 * This class supports building a request URL with optional query parameters.
 * Parameters can be added one by one, and the request can be converted into a
 * string that includes both the base request and its parameters.
 * </p>
 */
public class HttpRequest {

	private final String request;
	private final Map<String, List<String>> parameters;

	/**
	 * Constructs an {@code HttpRequest} with the specified base request URL.
	 *
	 * @param request the base URL or endpoint for the HTTP request
	 */
	public HttpRequest(String request) {
		this.request = request;
		this.parameters = new LinkedHashMap<>();
	}

	/**
	 * Adds a single parameter and its value to the request. If the parameter
	 * already exists, the value is appended to the list of values associated with
	 * that parameter.
	 *
	 * @param parameter the name of the parameter to add
	 * @param value     the value of the parameter, converted to a string (if not
	 *                  null)
	 * @return the current {@code HttpRequest} instance for method chaining
	 */
	public HttpRequest add(String parameter, Object value) {
		if (value != null) {
			if (!parameters.containsKey(parameter))
				parameters.put(parameter, new ArrayList<>());
			parameters.get(parameter).add(value.toString());
		}
		return this;
	}

	/**
	 * Adds multiple values for a single parameter. If the parameter already exists,
	 * the values are appended to the list of values associated with that parameter.
	 *
	 * @param parameter the name of the parameter to add
	 * @param values    one or more values for the parameter
	 * @return the current {@code HttpRequest} instance for method chaining
	 */
	public HttpRequest add(String parameter, String... values) {
		if (values.length > 0) {
			if (!parameters.containsKey(parameter))
				parameters.put(parameter, new ArrayList<>());
			for (String value : values)
				parameters.get(parameter).add(value);
		}
		return this;
	}

	/**
	 * Converts the request into a URL string with parameters, formatting the
	 * request with a query string.
	 *
	 * @return the formatted URL as a string, including query parameters if any
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(request);
		if (!parameters.isEmpty()) {
			string.append("?");
			LinkedList<String> keys = new LinkedList<>(parameters.keySet());
			while (!keys.isEmpty()) {
				String parameter = keys.removeFirst();
				string.append(parameter).append("=");
				LinkedList<String> values = new LinkedList<>(parameters.get(parameter));
				while (!values.isEmpty()) {
					String value = values.removeFirst();
					string.append(value);
					if (!values.isEmpty())
						string.append(",");
				}
				if (!keys.isEmpty())
					string.append("&");
			}
		}
		return string.toString();
	}

}
