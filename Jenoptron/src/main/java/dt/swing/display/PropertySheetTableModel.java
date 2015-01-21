package dt.swing.display;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.beanutils.BeanMap;
import org.taHjaj.wo.events.Observable;
import org.taHjaj.wo.events.ObservableMediator;

public class PropertySheetTableModel extends AbstractTableModel implements PropertyChangeListener, Observable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3082500086402409710L;

	final BeanMap beanMap;
	final List<String> keys;

	final Map< String, TableCellRenderer> tableCellRendererComponents;
	
	final Observable observable;
	
	@SuppressWarnings("unchecked")
	public PropertySheetTableModel( final Observable observable) {
		super();

		beanMap  = new BeanMap( observable);

		this.keys = new ArrayList<String>();

		final Set<String> keySet = beanMap.keySet();
		tableCellRendererComponents = new HashMap< String, TableCellRenderer>();

		for( String key : keySet) {
			if( (Boolean.class.getName()).equals( beanMap.get( key).getClass().getName())) {
				keys.add( key);

				final JCheckBox jCheckBox = new JCheckBox();

				jCheckBox.setSelected( (Boolean)beanMap.get( key));

				jCheckBox.setName( key);

				tableCellRendererComponents.put( key, new TableCellRenderer() {
					
					@Override
					public Component getTableCellRendererComponent(JTable table, Object value,
							boolean isSelected, boolean hasFocus, int row, int column) {
						// TODO Auto-generated method stub
						return jCheckBox;
					}
				});
			}
		}

		Collections.sort( keys);

		this.observable = observable;
		ObservableMediator.getInstance().register( observable, this);
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return keys.size();
	}

	@Override
	public Object getValueAt( final int rowIndex, final int columnIndex) {
		if( columnIndex == 0) {
			return keys.get( rowIndex); 
		} else {
			final String key = keys.get( rowIndex);
			return beanMap.get( key);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		ObservableMediator.getInstance().fire( this);
	}

	public TableCellEditor getCellEditor( final int row, final int column) {
		final TableCellEditor tableCellEditor;

		if( column == 1) {
			final String key = keys.get( row);

			if( "boolean".equals( beanMap.getType( key).getCanonicalName())) {
				JCheckBox checkBox = new JCheckBox();

				tableCellEditor = new DefaultCellEditor( checkBox);
			} else {
				tableCellEditor = null;
			}
		} else {
			tableCellEditor = null;
		}
		
		return tableCellEditor;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}

	public Object getValueColumnIdentifier() {
		return getColumnName( 1);
	}

	public boolean renderTableCell( final int row, final int column) {
		return column == 1;
	}

	public TableCellRenderer getCellRenderer(int row, int column) {
		return this.tableCellRendererComponents.values().iterator().next();
	}
}
