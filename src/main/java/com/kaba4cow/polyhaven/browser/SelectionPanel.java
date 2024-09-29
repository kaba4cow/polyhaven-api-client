package com.kaba4cow.polyhaven.browser;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.kaba4cow.polyhaven.browser.CategoryTable.CategoryTableListener;

public class SelectionPanel extends JPanel implements ItemListener, CategoryTableListener {

	private static final long serialVersionUID = 1L;

	private final PolyhavenBrowser browser;

	private final JComboBox<String> typeComboBox;
	private final JScrollPane scrollPane;
	private final CategoryTable categoryTable;

	public SelectionPanel(PolyhavenBrowser browser) {
		super();
		setLayout(new BorderLayout());

		this.browser = browser;

		JPanel typePanel = new JPanel();
		typePanel.setLayout(new BorderLayout());
		typeComboBox = new JComboBox<>();
		typeComboBox.addItemListener(this);
		typePanel.add(new JLabel("Type: "), BorderLayout.WEST);
		typePanel.add(typeComboBox, BorderLayout.CENTER);
		add(typePanel, BorderLayout.NORTH);

		categoryTable = new CategoryTable();
		categoryTable.addListener(this);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(categoryTable);
		add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource() == typeComboBox)
			updateCategories();
	}

	public void updateTypes() {
		try {
			typeComboBox.removeItemListener(this);
			String[] types = browser.getClient().getAssetTypes();
			for (String type : types)
				typeComboBox.addItem(type);
			typeComboBox.addItemListener(this);
			updateCategories();
		} catch (Exception e) {
			browser.showErrorDialog("Could not load asset types", () -> updateTypes());
		}
	}

	public void updateCategories() {
		scrollPane.getHorizontalScrollBar().setValue(0);
		scrollPane.getVerticalScrollBar().setValue(0);
		try {
			categoryTable.updateCategories(
					browser.getClient().getAssetCategories((String) typeComboBox.getSelectedItem()).keySet());
			updateAssets();
			updateAsset();
		} catch (Exception e) {
			browser.showErrorDialog("Could not load asset categories", () -> updateCategories());
		}
	}

	private void updateAssets() {
		browser.getAssetListPanel().updateAssets((String) typeComboBox.getSelectedItem(),
				categoryTable.getSelectedCategories());
	}

	public void updateAsset() {
	}

	@Override
	public void categoriesUpdated() {
		updateAssets();
	}

}