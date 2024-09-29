package com.kaba4cow.polyhaven.api.client.data.files;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * Represents a hierarchical structure of file objects and nested file trees
 * from Polyhaven. This class allows for the organization and retrieval of files
 * and their associated dependent files.
 */
public class PolyhavenFileTree {

	/**
	 * A map of direct file objects contained in this file tree, where the key is
	 * the file name and the value is the corresponding {@code PolyhavenFileObject}.
	 */
	private final Map<String, PolyhavenFileObject> files;

	/**
	 * A map of nested file trees contained in this file tree, where the key is the
	 * tree name and the value is the corresponding {@code PolyhavenFileTree}.
	 */
	private final Map<String, PolyhavenFileTree> trees;

	/**
	 * Constructs a {@code PolyhavenFileTree} from the provided JSON object.
	 *
	 * @param json a {@code JSONObject} containing the file tree details
	 */
	public PolyhavenFileTree(JSONObject json) {
		this.files = new HashMap<>();
		this.trees = new HashMap<>();
		for (String file : json.keySet()) {
			JSONObject jsonFile = json.getJSONObject(file);
			if (PolyhavenFileObject.isFile(jsonFile))
				files.put(file, new PolyhavenFileObject(jsonFile));
			else
				trees.put(file, new PolyhavenFileTree(jsonFile));
		}
	}

	/**
	 * Returns a map of direct file objects contained in this file tree.
	 *
	 * @return a map of file objects where the key is the file name
	 */
	public Map<String, PolyhavenFileObject> getFiles() {
		return files;
	}

	/**
	 * Returns a map of nested file trees contained in this file tree.
	 *
	 * @return a map of nested file trees where the key is the tree name
	 */
	public Map<String, PolyhavenFileTree> getTrees() {
		return trees;
	}

	/**
	 * Returns a string representation of the file tree, including the files and
	 * nested trees it contains.
	 *
	 * @return a string representation of the {@code PolyhavenFileTree}
	 */
	@Override
	public String toString() {
		return String.format("%s [files=%s, trees=%s]", getClass().getName(), files, trees);
	}
}
