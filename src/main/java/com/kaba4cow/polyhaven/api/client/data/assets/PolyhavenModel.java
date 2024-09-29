package com.kaba4cow.polyhaven.api.client.data.assets;

import java.util.Arrays;

import org.json.JSONObject;

import com.kaba4cow.polyhaven.api.client.JSONUtils;

/**
 * Represents a 3D model asset in the Polyhaven API. This class extends the
 * {@link PolyhavenAsset} and includes additional properties specific to model
 * assets.
 * 
 * <p>
 * This class provides details such as texel density, polygon count, and
 * dimensions of the 3D model.
 * </p>
 */
public class PolyhavenModel extends PolyhavenAsset {

	/**
	 * An array representing the texel density of the model. Texel density indicates
	 * the number of texture pixels per unit area of the model, which affects the
	 * quality of texture mapping.
	 */
	private final double[] texelDensity;

	/**
	 * The total number of polygons in the model. This value gives an indication of
	 * the complexity of the 3D model.
	 */
	private final int polyCount;

	/**
	 * An array representing the dimensions of the model in 3D space. The dimensions
	 * may include width, height, and depth.
	 */
	private final double[] dimensions;

	/**
	 * Constructs a new {@link PolyhavenModel} instance from the given ID and JSON
	 * object.
	 *
	 * @param id   the unique ID/slug of the asset
	 * @param json the JSON object containing the asset data
	 */
	public PolyhavenModel(String id, JSONObject json) {
		super(id, json);
		this.texelDensity = JSONUtils.getDoubleArray(json.optJSONArray("texel_density"));
		this.polyCount = json.optInt("polycount");
		this.dimensions = JSONUtils.getDoubleArray(json.optJSONArray("dimensions"));
	}

	/**
	 * Returns the texel density of the model.
	 *
	 * @return an array containing the texel density values
	 */
	public double[] getTexelDensity() {
		return texelDensity;
	}

	/**
	 * Returns the total number of polygons in the model.
	 *
	 * @return the polygon count
	 */
	public int getPolyCount() {
		return polyCount;
	}

	/**
	 * Returns the dimensions of the model in 3D space.
	 *
	 * @return an array containing the dimensions of the model
	 */
	public double[] getDimensions() {
		return dimensions;
	}

	/**
	 * Returns a string representation of the PolyhavenModel object.
	 *
	 * @return a string representation of the model asset
	 */
	@Override
	public String toString() {
		return String.format("%s [texelDensity=%s, polyCount=%s, dimensions=%s, %s]", getClass().getName(),
				Arrays.toString(texelDensity), polyCount, Arrays.toString(dimensions), super.toString());
	}
}
