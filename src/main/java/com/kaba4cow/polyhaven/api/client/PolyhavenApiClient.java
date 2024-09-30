package com.kaba4cow.polyhaven.api.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kaba4cow.polyhaven.api.client.data.PolyhavenAuthor;
import com.kaba4cow.polyhaven.api.client.data.assets.PolyhavenAsset;
import com.kaba4cow.polyhaven.api.client.data.files.PolyhavenFileTree;
import com.kaba4cow.polyhaven.api.client.http.HttpException;
import com.kaba4cow.polyhaven.api.client.http.HttpRequest;
import com.kaba4cow.polyhaven.api.client.http.HttpResponse;

/**
 * Client for interacting with the Polyhaven API to fetch assets, authors, and
 * related metadata.
 * 
 * <p>
 * This class provides methods to retrieve asset types, detailed asset
 * information, file trees, authors, and image data. It handles HTTP requests
 * and parses JSON responses.
 * </p>
 * 
 * <p>
 * API base URL: https://api.polyhaven.com/
 * </p>
 * 
 * @see PolyhavenAsset
 * @see PolyhavenAuthor
 * @see PolyhavenFileTree
 * @see HttpClient
 */
public class PolyhavenApiClient {

	private static final int RESPONSE_OK = 200;
	private static final int RESPONSE_BAD_REQUEST = 400;
	private static final int RESPONSE_NOT_FOUND = 404;

	private final String url;

	/**
	 * Constructs a new {@code PolyhavenApiClient} with specified URL to interact
	 * with the Polyhaven API.
	 */
	public PolyhavenApiClient(String url) {
		this.url = url;
	}

	/**
	 * Constructs a new {@code PolyhavenApiClient} with default URL
	 * ({@code https://api.polyhaven.com/}) to interact with the Polyhaven API.
	 */
	public PolyhavenApiClient() {
		this("https://api.polyhaven.com/");
	}

	/**
	 * Retrieves an array of available asset types from the API.
	 * 
	 * @return an array of asset types available on Polyhaven
	 * @throws IOException   if an I/O error occurs
	 * @throws HttpException if the API responds with an error code
	 */
	public String[] getAssetTypes() throws IOException, HttpException {
		HttpRequest request = new HttpRequest("types");
		HttpResponse response = new HttpResponse(url + request);
		switch (response.getCode()) {
		case RESPONSE_OK:
			JSONArray json = response.getJSONArray();
			String[] types = new String[json.length()];
			for (int i = 0; i < json.length(); i++)
				types[i] = json.getString(i);
			return types;
		default:
			throw new HttpException(response, "Unsupported response code");
		}
	}

	/**
	 * Retrieves a list of assets filtered by type and categories.
	 * 
	 * @param type       the type of assets to filter by (e.g., 'hdris', 'textures',
	 *                   'models', 'all')
	 * @param categories an array of categories to filter the assets by
	 * @return a map of asset IDs to {@code PolyhavenAsset} objects, including their
	 *         metadata
	 * @throws IOException   if an I/O error occurs
	 * @throws HttpException if the API responds with an error code
	 */
	public Map<String, PolyhavenAsset> getAssets(String type, String... categories) throws IOException, HttpException {
		HttpRequest request = new HttpRequest("assets").add("t", type).add("c", categories);
		HttpResponse response = new HttpResponse(url + request);
		switch (response.getCode()) {
		case RESPONSE_OK:
			JSONObject json = response.getJSONObject();
			Map<String, PolyhavenAsset> assets = new LinkedHashMap<>();
			for (String id : json.keySet())
				assets.put(id, PolyhavenAsset.createAsset(id, json.getJSONObject(id)));
			return assets;
		case RESPONSE_BAD_REQUEST:
			throw new HttpException(response, "Bad request");
		default:
			throw new HttpException(response, "Unsupported response code");
		}
	}

	/**
	 * Retrieves detailed information about a specific asset by its unique ID.
	 * 
	 * @param id the unique ID/slug of the asset
	 * @return a {@code PolyhavenAsset} object containing detailed information about
	 *         the asset
	 * @throws IOException   if an I/O error occurs
	 * @throws HttpException if the API responds with an error code
	 */
	public PolyhavenAsset getAsset(String id) throws IOException, HttpException {
		HttpRequest request = new HttpRequest("info/" + id);
		HttpResponse response = new HttpResponse(url + request);
		switch (response.getCode()) {
		case RESPONSE_OK:
			JSONObject json = response.getJSONObject();
			return PolyhavenAsset.createAsset(id, json);
		case RESPONSE_BAD_REQUEST:
			throw new HttpException(response, "Bad request");
		case RESPONSE_NOT_FOUND:
			throw new HttpException(response, "No asset found with id " + id);
		default:
			throw new HttpException(response, "Unsupported response code");
		}
	}

	/**
	 * Retrieves a file tree for the specified asset, organized by resolution and
	 * file type.
	 * 
	 * @param id the unique ID/slug of the asset
	 * @return a {@code PolyhavenFileTree} containing the available files for the
	 *         asset
	 * @throws IOException   if an I/O error occurs
	 * @throws HttpException if the API responds with an error code
	 */
	public PolyhavenFileTree getAssetFileTree(String id) throws IOException, HttpException {
		HttpRequest request = new HttpRequest("files/" + id);
		HttpResponse response = new HttpResponse(url + request);
		switch (response.getCode()) {
		case RESPONSE_OK:
			JSONObject json = response.getJSONObject();
			return new PolyhavenFileTree(json);
		case RESPONSE_BAD_REQUEST:
			throw new HttpException(response, "Bad request");
		case RESPONSE_NOT_FOUND:
			throw new HttpException(response, "No asset found with id " + id);
		default:
			throw new HttpException(response, "Unsupported response code");
		}
	}

	/**
	 * Retrieves information about a specific author by their unique ID.
	 * 
	 * @param id the unique ID of the author
	 * @return a {@code PolyhavenAuthor} containing data about the requested author
	 * @throws IOException   if an I/O error occurs
	 * @throws HttpException if the API responds with an error code
	 */
	public PolyhavenAuthor getAuthor(String id) throws IOException, HttpException {
		HttpRequest request = new HttpRequest("author/" + id);
		HttpResponse response = new HttpResponse(url + request);
		switch (response.getCode()) {
		case RESPONSE_OK:
			JSONObject json = response.getJSONObject();
			return new PolyhavenAuthor(id, json);
		case RESPONSE_BAD_REQUEST:
			throw new HttpException(response, "Bad request");
		case RESPONSE_NOT_FOUND:
			throw new HttpException(response, "No author found with id " + id);
		default:
			throw new HttpException(response, "Unsupported response code");
		}
	}

	/**
	 * Retrieves available categories for a specified asset type.
	 * 
	 * @param type one of the supported asset types ('hdris', 'textures', or
	 *             'models')
	 * @param in   an array of categories to filter the results (optional)
	 * @return a map of category names to the number of assets available in each
	 *         category
	 * @throws IOException   if an I/O error occurs
	 * @throws HttpException if the API responds with an error code
	 */
	public Map<String, Integer> getAssetCategories(String type, String... in) throws IOException, HttpException {
		HttpRequest request = new HttpRequest("categories/" + type).add("in", in);
		HttpResponse response = new HttpResponse(url + request);
		switch (response.getCode()) {
		case RESPONSE_OK:
			JSONObject json = response.getJSONObject();
			Map<String, Integer> categories = new LinkedHashMap<>();
			for (String category : json.keySet())
				categories.put(category, json.getInt(category));
			return categories;
		case RESPONSE_BAD_REQUEST:
			throw new HttpException(response, "Bad request");
		default:
			throw new HttpException(response, "Unsupported response code");
		}
	}

	/**
	 * Downloads an image from the specified URL.
	 * 
	 * @param url the URL of the image to download
	 * @return a {@code BufferedImage} representing the downloaded image
	 * @throws IOException if an I/O error occurs while fetching the image
	 */

	public BufferedImage getImage(String url) throws IOException {
		return new HttpResponse(url).getImage();
	}

}
