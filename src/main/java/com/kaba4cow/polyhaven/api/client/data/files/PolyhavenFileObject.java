package com.kaba4cow.polyhaven.api.client.data.files;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * Represents a file object from Polyhaven, containing details such as the
 * file's URL, MD5 checksum, size, and any dependent files that should be
 * included when downloading.
 */
public class PolyhavenFileObject {

	/**
	 * Direct URL to download this file
	 */
	private final String url;

	/**
	 * MD5 checksum for verifying file integrity
	 */
	private final String md5;

	/**
	 * Size of the file in bytes
	 */
	private final long size;

	/**
	 * A list of files that this file depends on and should be included when
	 * downloaded, typically textures that the model/texture uses
	 */
	private final Map<String, PolyhavenFileTree> includes;

	/**
	 * Constructs a {@code PolyhavenFileObject} from the provided JSON object.
	 *
	 * @param json a {@code JSONObject} containing the file details
	 */
	public PolyhavenFileObject(JSONObject json) {
		this.url = json.getString("url");
		this.md5 = json.getString("md5");
		this.size = json.getLong("size");
		this.includes = new HashMap<>();
		if (json.has("include")) {
			JSONObject jsonInclude = json.getJSONObject("include");
			for (String file : jsonInclude.keySet())
				includes.put(file, new PolyhavenFileTree(jsonInclude));
		}
	}

	/**
	 * Checks if the given JSON object represents a valid file object.
	 *
	 * @param json the {@code JSONObject} to check
	 * @return {@code true} if the JSON object contains the required fields for a
	 *         file; {@code false} otherwise
	 */
	public static boolean isFile(JSONObject json) {
		return json.has("url") && json.has("md5") && json.has("size");
	}

	/**
	 * Returns the URL to download the file.
	 *
	 * @return the download URL of the file
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Returns the MD5 checksum of the file for integrity verification.
	 *
	 * @return the MD5 checksum of the file
	 */
	public String getMd5() {
		return md5;
	}

	/**
	 * Returns the size of the file in bytes.
	 *
	 * @return the size of the file in bytes
	 */
	public long getSize() {
		return size;
	}

	/**
	 * Returns a map of files that should be included when downloading this file.
	 *
	 * @return a map of dependent files associated with this file
	 */
	public Map<String, PolyhavenFileTree> getIncludes() {
		return includes;
	}

	/**
	 * Returns a string representation of the file object, including the URL, MD5
	 * checksum, size, and included files.
	 *
	 * @return a string representation of the {@code PolyhavenFileObject}
	 */
	@Override
	public String toString() {
		return String.format("%s [url=%s, md5=%s, size=%s, includes=%s]", getClass().getName(), url, md5, size,
				includes);
	}

}
