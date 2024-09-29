package com.kaba4cow.polyhaven.browser;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.kaba4cow.polyhaven.api.client.data.assets.PolyhavenAsset;

public class AssetListPanel extends JPanel   {

	private static final long serialVersionUID = 1L;

	private final PolyhavenBrowser browser;

	private Collection<PolyhavenAsset> input;

	private final SearchTextField searchTextField;
	private final JScrollPane scrollPane;
	private final AssetList assetList;

	public AssetListPanel(PolyhavenBrowser browser) {
		super();
		this.browser = browser;
		input = new ArrayList<>();
		setLayout(new BorderLayout());
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());
		searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
		searchPanel.add(searchTextField = new SearchTextField(), BorderLayout.CENTER);
		add(searchPanel, BorderLayout.NORTH);
		assetList = new AssetList();
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(assetList);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void updateAssets(String type, String[] categories) {
		try {
			searchTextField.setText("");
			input = browser.getClient().getAssets(type, categories).values();
			updateAssets();
		} catch (Exception e) {
			browser.showErrorDialog("Could not load assets", () -> updateAssets());
		}
	}

	private void updateAssets() {
		scrollPane.getHorizontalScrollBar().setValue(0);
		scrollPane.getVerticalScrollBar().setValue(0);
		Collection<PolyhavenAsset> output = new ArrayList<>();
		String text = searchTextField.getText().toLowerCase();
		if (text.isEmpty())
			output.addAll(input);
		else
			for (PolyhavenAsset asset : input)
				if (asset.getName().toLowerCase().contains(text))
					output.add(asset);
		assetList.setAssets(output);
	}

	private class SearchTextField extends JTextField implements DocumentListener {

		private static final long serialVersionUID = 1L;

		public SearchTextField() {
			super();
			getDocument().addDocumentListener(this);
		}

		@Override
		public void insertUpdate(DocumentEvent event) {
			updateAssets();
		}

		@Override
		public void removeUpdate(DocumentEvent event) {
			updateAssets();
		}

		@Override
		public void changedUpdate(DocumentEvent event) {
			updateAssets();
		}

	}

}
