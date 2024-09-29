package com.kaba4cow.polyhaven.api.client.data.assets;

import java.util.Arrays;
import java.util.Map;

import org.json.JSONObject;

import com.kaba4cow.polyhaven.api.client.JSONUtils;

/**
 * Represents a generic asset from Polyhaven, providing common attributes and
 * methods for different types of assets such as HDRIs, textures, and models.
 */
public abstract class PolyhavenAsset {

	/**
	 * The unique ID/slug of the asset.
	 */
	private final String id;

	/**
	 * The human-readable/display name of the asset.
	 */
	private final String name;

	/**
	 * The type of the asset, represented as an integer.
	 */
	private final int type;

	/**
	 * The epoch timestamp in seconds of when this asset was published.
	 */
	private final long datePublished;

	/**
	 * The number of times this asset has been downloaded. Useful for sorting by
	 * popularity.
	 */
	private final long downloadCount;

	/**
	 * A SHA1 hash of the files object (from the /files endpoint), which will change
	 * whenever the files are updated.
	 */
	private final String filesHash;

	/**
	 * A map containing information about who created this asset and their
	 * contributions.
	 */
	private final Map<String, String> authors;

	/**
	 * Indicates whether or not this asset was donated free of charge.
	 */
	private final boolean donated;

	/**
	 * An array of categories that this asset belongs to.
	 */
	private final String[] categories;

	/**
	 * An array of tags associated with this asset to assist in search matches.
	 */
	private final String[] tags;

	/**
	 * The URL of the preview image thumbnail for this asset, provided in .webp
	 * format.
	 */
	private final String thumbnailUrl;

	/**
	 * The maximum resolution supported by this asset.
	 */
	private final int[] maxResolution;

	/**
	 * Constructs a {@code PolyhavenAsset} from the provided ID and JSON object.
	 *
	 * @param id   the unique ID/slug of the asset
	 * @param json a {@code JSONObject} containing the asset details
	 */
	public PolyhavenAsset(String id, JSONObject json) {
		this.id = id;
		this.name = json.getString("name");
		this.type = json.getInt("type");
		this.datePublished = json.getLong("date_published");
		this.downloadCount = json.getLong("download_count");
		this.filesHash = json.getString("files_hash");
		this.authors = JSONUtils.getStringMap(json.getJSONObject("authors"));
		this.donated = json.optBoolean("donated");
		this.categories = JSONUtils.getStringArray(json.getJSONArray("categories"));
		this.tags = JSONUtils.getStringArray(json.getJSONArray("tags"));
		this.thumbnailUrl = json.getString("thumbnail_url");
		this.maxResolution = JSONUtils.getIntArray(json.optJSONArray("max_resolution"));
	}

	/**
	 * Creates a specific type of asset based on the provided JSON object.
	 *
	 * @param id   the unique ID of the asset
	 * @param json a {@code JSONObject} containing the asset details
	 * @return a {@code PolyhavenAsset} instance of the appropriate type, or null if
	 *         the type is unknown
	 */
	public static PolyhavenAsset createAsset(String id, JSONObject json) {
		int type = json.getInt("type");
		switch (type) {
		case 0:
			return new PolyhavenHDRI(id, json);
		case 1:
			return new PolyhavenTexture(id, json);
		case 2:
			return new PolyhavenModel(id, json);
		default:
			return null;
		}
	}

	/**
	 * Returns the unique ID/slug of the asset.
	 *
	 * @return the asset ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the human-readable/display name of the asset.
	 *
	 * @return the asset name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the type of the asset as an integer.
	 *
	 * @return the asset type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Returns the epoch timestamp in seconds of when this asset was published.
	 *
	 * @return the publication date in epoch seconds
	 */
	public long getDatePublished() {
		return datePublished;
	}

	/**
	 * Returns the number of times this asset has been downloaded.
	 *
	 * @return the download count
	 */
	public long getDownloadCount() {
		return downloadCount;
	}

	/**
	 * Returns the SHA1 hash of the files object from the /files endpoint.
	 *
	 * @return the files hash
	 */
	public String getFilesHash() {
		return filesHash;
	}

	/**
	 * Returns a map containing information about the authors of the asset.
	 *
	 * @return a map of authors and their contributions
	 */
	public Map<String, String> getAuthors() {
		return authors;
	}

	/**
	 * Indicates whether or not this asset was donated free of charge.
	 *
	 * @return true if the asset was donated, false otherwise
	 */
	public boolean isDonated() {
		return donated;
	}

	/**
	 * Returns an array of categories that this asset belongs to.
	 *
	 * @return an array of asset categories
	 */
	public String[] getCategories() {
		return categories;
	}

	/**
	 * Returns an array of tags associated with this asset for search purposes.
	 *
	 * @return an array of asset tags
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * Returns the URL of the preview image thumbnail for this asset.
	 *
	 * @return the thumbnail URL
	 */
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	/**
	 * Returns the maximum resolution supported by this asset.
	 *
	 * @return an array representing the maximum resolution
	 */
	public int[] getMaxResolution() {
		return maxResolution;
	}

	/**
	 * Returns a string representation of the asset, including its attributes.
	 *
	 * @return a string representation of the {@code PolyhavenAsset}
	 */
	@Override
	public String toString() {
		return String.format(
				"%s [id=%s, name=%s, type=%s, datePublished=%s, downloadCount=%s, filesHash=%s, authors=%s, donated=%s, categories=%s, tags=%s, thumbnailUrl=%s, maxResolution=%s]",
				getClass().getName(), id, name, type, datePublished, downloadCount, filesHash, authors, donated,
				Arrays.toString(categories), Arrays.toString(tags), thumbnailUrl, Arrays.toString(maxResolution));
	}
}
