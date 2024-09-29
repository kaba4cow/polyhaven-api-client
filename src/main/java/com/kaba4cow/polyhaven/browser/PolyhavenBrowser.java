package com.kaba4cow.polyhaven.browser;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.kaba4cow.polyhaven.api.client.PolyhavenClient;

public class PolyhavenBrowser extends JFrame {

	private static final long serialVersionUID = 1L;

	private final PolyhavenClient client;

	private final SelectionPanel selectionPanel;
	private final AssetListPanel assetListPanel;

	private PolyhavenBrowser() {
		super();
		client = new PolyhavenClient();
		setLayout(new BorderLayout());

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(0.25d);
		splitPane.setBottomComponent(assetListPanel = new AssetListPanel(this));
		splitPane.setTopComponent(selectionPanel = new SelectionPanel(this));
		add(splitPane, BorderLayout.CENTER);

		setPreferredSize(new Dimension(640, 480));
		pack();
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Polyhaven Browser");
		setVisible(true);
		selectionPanel.updateTypes();
	}

	public void showErrorDialog(String message, Runnable callback) {
		SwingUtilities.invokeLater(() -> {
			String[] options = { "Try again", "Exit" };
			int option = JOptionPane.showOptionDialog(this, message, "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			switch (option) {
			case 0:
				callback.run();
				break;
			case 1:
				dispose();
				break;
			}
		});
	}

	public PolyhavenClient getClient() {
		return client;
	}

	public SelectionPanel getSelectionPanel() {
		return selectionPanel;
	}

	public AssetListPanel getAssetListPanel() {
		return assetListPanel;
	}

	public static void main(String[] args) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		new PolyhavenBrowser();
	}

}
