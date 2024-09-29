package com.kaba4cow.polyhaven.browser.imageloader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.SwingUtilities;

import com.kaba4cow.polyhaven.api.client.http.HttpResponse;

public class ImageLoader {

	private static final Map<String, ImageCollection> images = new ConcurrentHashMap<>();
	private static final ExecutorService executor = Executors.newFixedThreadPool(2);
	private static final Map<String, Future<?>> futures = new ConcurrentHashMap<>();

	private ImageLoader() {
	}

	public static void loadImage(String url, ImageLoaderListener listener) {
		if (images.containsKey(url))
			listener.imageLoaded(images.get(url));
		else if (!futures.containsKey(url)) {
			Future<?> future = executor.submit(() -> {
				futures.remove(url);
				try {
					if (Thread.currentThread().isInterrupted())
						return;
					ImageCollection image = new ImageCollection(new HttpResponse(url).getImage());
					images.put(url, image);
					SwingUtilities.invokeLater(() -> listener.imageLoaded(image));
				} catch (Exception e) {
					SwingUtilities.invokeLater(() -> listener.imageLoaded(null));
				}
			});
			futures.put(url, future);
		}
	}

	public static void cancelLoading() {
		for (Future<?> future : futures.values())
			future.cancel(true);
		futures.clear();
	}

}
