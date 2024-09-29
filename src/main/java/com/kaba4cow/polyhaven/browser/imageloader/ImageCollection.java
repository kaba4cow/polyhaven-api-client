package com.kaba4cow.polyhaven.browser.imageloader;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageCollection {

	private final Image originalImage;
	private final Map<Integer, Image> scaledImages;

	public ImageCollection(Image image) {
		if (image == null)
			throw new IllegalArgumentException("image cannot be null");
		this.originalImage = image;
		this.scaledImages = new HashMap<>();
	}

	public Image getOriginalImage() {
		return originalImage;
	}

	public Image getScaledImage(int height) {
		if (scaledImages.containsKey(height))
			return scaledImages.get(height);
		else {
			int width = (originalImage.getWidth(null) * height) / originalImage.getHeight(null);
			Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			scaledImages.put(height, scaledImage);
			return scaledImage;
		}
	}

}