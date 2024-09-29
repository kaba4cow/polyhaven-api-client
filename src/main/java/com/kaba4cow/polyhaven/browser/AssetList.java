package com.kaba4cow.polyhaven.browser;

import java.util.Collection;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import com.kaba4cow.polyhaven.api.client.data.assets.PolyhavenAsset;
import com.kaba4cow.polyhaven.browser.imageloader.ImageLoader;

public class AssetList extends JList<PolyhavenAsset> {

	private static final long serialVersionUID = 1L;

	private static final int IMAGE_SIZE = 64;

	private final DefaultListModel<PolyhavenAsset> model;

	public AssetList() {
		super();
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setModel(model = new DefaultListModel<>());
		setCellRenderer(new AssetListCellRenderer());
		setFixedCellHeight(IMAGE_SIZE);
	}

	public void setAssets(Collection<PolyhavenAsset> assets) {
		ImageLoader.cancelLoading();
		model.clear();
		for (PolyhavenAsset asset : assets)
			model.addElement(asset);
	}

	private class AssetListCellRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;

		private final JLabel label;

		public AssetListCellRenderer() {
			super();
			label = new JLabel();
			label.setOpaque(true);
		}

		@Override
		public JLabel getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if (isSelected) {
				label.setBackground(list.getSelectionBackground());
				label.setForeground(list.getSelectionForeground());
			} else {
				label.setBackground(list.getBackground());
				label.setForeground(list.getForeground());
			}
			PolyhavenAsset asset = model.getElementAt(index);
			label.setText(asset.getId());
			label.setIcon(null);
			ImageLoader.loadImage(asset.getThumbnailUrl(), image -> {
				if (image != null) {
					label.setIcon(new ImageIcon(image.getScaledImage(IMAGE_SIZE)));
					list.repaint();
				}
			});
			return label;
		}

	}

}
