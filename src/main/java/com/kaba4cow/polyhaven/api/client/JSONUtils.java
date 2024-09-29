package com.kaba4cow.polyhaven.api.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Utility class for converting JSON data structures (such as {@link JSONArray}
 * and {@link JSONObject}) into Java native data types. This class provides
 * methods to extract arrays and maps from JSON objects.
 */
public class JSONUtils {

	private JSONUtils() {
	}

	/**
	 * Converts a {@link JSONArray} into an array of {@link String} objects. If the
	 * input is {@code null}, an empty string array is returned.
	 *
	 * @param json the {@link JSONArray} to convert, may be {@code null}
	 * @return an array of strings extracted from the JSON array, or an empty array
	 *         if the input is {@code null}
	 */
	public static String[] getStringArray(JSONArray json) {
		if (json == null)
			return new String[0];
		int length = json.length();
		String[] array = new String[length];
		for (int i = 0; i < length; i++)
			array[i] = json.getString(i);
		return array;
	}

	/**
	 * Converts a {@link JSONArray} into an array of integers. If the input is
	 * {@code null}, an empty integer array is returned.
	 *
	 * @param json the {@link JSONArray} to convert, may be {@code null}
	 * @return an array of integers extracted from the JSON array, or an empty array
	 *         if the input is {@code null}
	 */
	public static int[] getIntArray(JSONArray json) {
		if (json == null)
			return new int[0];
		int length = json.length();
		int[] array = new int[length];
		for (int i = 0; i < length; i++)
			array[i] = json.getInt(i);
		return array;
	}

	/**
	 * Converts a {@link JSONArray} into an array of doubles. If the input is
	 * {@code null}, an empty double array is returned.
	 *
	 * @param json the {@link JSONArray} to convert, may be {@code null}
	 * @return an array of doubles extracted from the JSON array, or an empty array
	 *         if the input is {@code null}
	 */
	public static double[] getDoubleArray(JSONArray json) {
		if (json == null)
			return new double[0];
		int length = json.length();
		double[] array = new double[length];
		for (int i = 0; i < length; i++)
			array[i] = json.getDouble(i);
		return array;
	}

	/**
	 * Converts a {@link JSONObject} into a {@link Map} with string keys and string
	 * values. If the input is {@code null}, an empty map is returned.
	 *
	 * @param json the {@link JSONObject} to convert, may be {@code null}
	 * @return a map of key-value pairs extracted from the JSON object, or an empty
	 *         map if the input is {@code null}
	 */
	public static Map<String, String> getStringMap(JSONObject json) {
		if (json == null)
			return new HashMap<>();
		Map<String, String> map = new HashMap<>();
		Set<String> keys = json.keySet();
		for (String key : keys)
			map.put(key, json.getString(key));
		return map;
	}

}
