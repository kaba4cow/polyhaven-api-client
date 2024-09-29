package com.kaba4cow.polyhaven.browser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class CategoryTable extends JTable {

	private static final long serialVersionUID = 1L;

	private final Set<CategoryTableListener> listeners;
	private final CategoryTableModel model;

	public CategoryTable() {
		super();
		listeners = new HashSet<>();
		setModel(model = new CategoryTableModel());
		JCheckBox checkBox = new JCheckBox();
		checkBox.setHorizontalAlignment(SwingConstants.CENTER);
		getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(checkBox));
		getColumnModel().getColumn(0).setCellRenderer(getDefaultRenderer(Boolean.class));
		getColumnModel().getColumn(0).setMaxWidth(16);
		setTableHeader(null);
	}

	public void addListener(CategoryTableListener listener) {
		listeners.add(listener);
	}

	public void updateCategories(Set<String> categories) {
		List<String> list = new ArrayList<>(categories);
		Collections.sort(list);
		model.setCategories(list);
	}

	public String[] getSelectedCategories() {
		return model.getSelectedCategories();
	}

	private class CategoryTableModel extends AbstractTableModel implements TableModelListener {

		private static final long serialVersionUID = 1L;

		private Object[][] data = new Object[0][2];

		public CategoryTableModel() {
			super();
			addTableModelListener(this);
		}

		@Override
		public void tableChanged(TableModelEvent event) {
			if (event.getType() == TableModelEvent.UPDATE && event.getColumn() == 0)
				for (CategoryTableListener listener : listeners)
					listener.categoriesUpdated();
		}

		public void setCategories(List<String> categories) {
			data = new Object[categories.size()][2];
			for (int i = 0; i < data.length; i++) {
				data[i][0] = false;
				data[i][1] = categories.get(i);
			}
			fireTableDataChanged();
		}

		public String[] getSelectedCategories() {
			List<String> selected = new ArrayList<>();
			for (Object[] row : data)
				if ((Boolean) row[0])
					selected.add((String) row[1]);
			String[] array = new String[selected.size()];
			for (int i = 0; i < array.length; i++)
				array[i] = selected.get(i);
			return array;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = value;
			fireTableCellUpdated(rowIndex, columnIndex);
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 0;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return columnIndex == 0 ? Boolean.class : String.class;
		}

	}

	public static interface CategoryTableListener {

		public void categoriesUpdated();

	}

}
