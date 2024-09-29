package com.kaba4cow.polyhaven.api.client.data.assets;

import java.util.Arrays;

import org.json.JSONObject;

import com.kaba4cow.polyhaven.api.client.JSONUtils;

/**
 * Represents a High Dynamic Range Image (HDRI) asset in the Polyhaven API. This
 * class extends the {@link PolyhavenAsset} and includes additional properties
 * specific to HDRI assets.
 * 
 * <p>
 * This class provides details such as white balance, backplates availability,
 * exposure brackets captured, GPS coordinates, and the date the HDRI was taken.
 * </p>
 * 
 * <p>
 * Note: Some properties may not be available for all HDRI assets.
 * </p>
 */
public class PolyhavenHDRI extends PolyhavenAsset {

	/**
	 * The white balance in Kelvin at which this HDRI and any included backplates
	 * were shot. This value may not be present for all assets.
	 */
	private final int whiteBalance;

	/**
	 * Indicates whether there are backplates available for this HDRI.
	 */
	private final boolean backplates;

	/**
	 * The number of exposure brackets captured when shooting this HDRI,
	 * representing the difference between the brightest and darkest shots. This
	 * value indicates the dynamic range of the HDRI.
	 */
	private final int evsCap;

	/**
	 * Decimal latitude and longitude GPS coordinates for the location where the
	 * HDRI was captured.
	 */
	private final double[] coords;

	/**
	 * A legacy (and generally untrustworthy) epoch timestamp of when this HDRI was
	 * taken. Due to timezone issues, this data may be inaccurate and should not be
	 * relied upon in its current form. Future solutions may improve this data.
	 */
	private final long dateTaken;

	/**
	 * Constructs a new {@link PolyhavenHDRI} instance from the given ID and JSON
	 * object.
	 *
	 * @param id   the unique ID/slug of the asset
	 * @param json the JSON object containing the asset data
	 */
	public PolyhavenHDRI(String id, JSONObject json) {
		super(id, json);
		this.whiteBalance = json.optInt("whitebalance");
		this.backplates = json.optBoolean("backplates");
		this.evsCap = json.getInt("evs_cap");
		this.coords = JSONUtils.getDoubleArray(json.optJSONArray("coords"));
		this.dateTaken = json.optLong("date_taken");
	}

	/**
	 * Returns the white balance in Kelvin for this HDRI.
	 *
	 * @return the white balance
	 */
	public int getWhiteBalance() {
		return whiteBalance;
	}

	/**
	 * Indicates whether backplates are available for this HDRI.
	 *
	 * @return true if backplates are available, false otherwise
	 */
	public boolean isBackplates() {
		return backplates;
	}

	/**
	 * Returns the number of exposure brackets captured when shooting this HDRI.
	 *
	 * @return the exposure brackets count
	 */
	public int getEvsCap() {
		return evsCap;
	}

	/**
	 * Returns the decimal latitude and longitude GPS coordinates of the HDRI
	 * capture location.
	 *
	 * @return an array containing the GPS coordinates
	 */
	public double[] getCoords() {
		return coords;
	}

	/**
	 * Returns the legacy epoch timestamp of when this HDRI was taken.
	 *
	 * @return the date taken in epoch seconds
	 */
	public long getDateTaken() {
		return dateTaken;
	}

	/**
	 * Returns a string representation of the PolyhavenHDRI object.
	 *
	 * @return a string representation of the HDRI asset
	 */
	@Override
	public String toString() {
		return String.format("%s [whiteBalance=%s, backplates=%s, evsCap=%s, coords=%s, dateTaken=%s, %s]",
				getClass().getName(), whiteBalance, backplates, evsCap, Arrays.toString(coords), dateTaken,
				super.toString());
	}
}
