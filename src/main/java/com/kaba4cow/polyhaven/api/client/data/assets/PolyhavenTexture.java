package com.kaba4cow.polyhaven.api.client.data.assets;

import java.util.Arrays;

import org.json.JSONObject;

import com.kaba4cow.polyhaven.api.client.JSONUtils;

/**
 * Represents a texture asset in the Polyhaven API. This class extends the
 * {@link PolyhavenAsset} and includes properties specific to texture assets.
 * 
 * <p>
 * This class provides details such as the dimensions of the texture on each
 * axis in millimeters.
 * </p>
 */
public class PolyhavenTexture extends PolyhavenAsset {

	/**
	 * An array representing the dimensions of this texture asset on each axis in
	 * millimeters.
	 */
	private final int[] dimensions;

	/**
	 * Constructs a new {@link PolyhavenTexture} instance from the given ID and JSON
	 * object.
	 *
	 * @param id   the unique ID/slug of the asset
	 * @param json the JSON object containing the asset data
	 */
	public PolyhavenTexture(String id, JSONObject json) {
		super(id, json);
		this.dimensions = JSONUtils.getIntArray(json.getJSONArray("dimensions"));
	}

	/**
	 * Returns the dimensions of the texture asset on each axis in millimeters.
	 *
	 * @return an array containing the dimensions of the texture
	 */
	public int[] getDimensions() {
		return dimensions;
	}

	/**
	 * Returns a string representation of the PolyhavenTexture object.
	 *
	 * @return a string representation of the texture asset
	 */
	@Override
	public String toString() {
		return String.format("%s [dimensions=%s, %s]", getClass().getName(), Arrays.toString(dimensions),
				super.toString());
	}
}