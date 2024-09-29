package com.kaba4cow.polyhaven.api.client.http;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Represents an HTTP response received after making a request to a given URL.
 * Provides utility methods for reading the response as various types, such as
 * strings, JSON objects, arrays, or images.
 */
public class HttpResponse {

	private final HttpURLConnection connection;
	private final int code;

	/**
	 * Constructs an {@code HttpResponse} by sending a GET request to the specified
	 * URL.
	 *
	 * @param url the URL to send the GET request to
	 * @throws IOException if an I/O error occurs when creating or receiving the
	 *                     connection
	 */
	public HttpResponse(String url) throws IOException {
		this.connection = (HttpURLConnection) new URL(url).openConnection();
		this.connection.setRequestMethod("GET");
		this.code = connection.getResponseCode();
	}

	/**
	 * Returns the HTTP status code of the response.
	 *
	 * @return the HTTP status code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Returns the input stream of the response, which can be used to read the
	 * response body.
	 *
	 * @return the input stream of the response
	 * @throws IOException if an I/O error occurs when accessing the input stream
	 */
	public InputStream getInputStream() throws IOException {
		return connection.getInputStream();
	}

	/**
	 * Reads the response body as a string.
	 *
	 * @return the response body as a string
	 * @throws IOException if an I/O error occurs when reading the input stream
	 */
	public String getString() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream()));
		StringBuilder string = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null)
			string.append(line).append('\n');
		reader.close();
		return string.toString();
	}

	/**
	 * Reads the response body as a JSON object.
	 *
	 * @return the response body as a {@code JSONObject}
	 * @throws IOException if an I/O error occurs when reading the input stream
	 */
	public JSONObject getJSONObject() throws IOException {
		return new JSONObject(getString());
	}

	/**
	 * Reads the response body as a JSON array.
	 *
	 * @return the response body as a {@code JSONArray}
	 * @throws IOException if an I/O error occurs when reading the input stream
	 */
	public JSONArray getJSONArray() throws IOException {
		return new JSONArray(getString());
	}

	/**
	 * Reads the response body as an image.
	 *
	 * @return the response body as a {@code BufferedImage}
	 * @throws IOException if an I/O error occurs when reading the input stream or
	 *                     parsing the image
	 */
	public BufferedImage getImage() throws IOException {
		return ImageIO.read(getInputStream());
	}

}
