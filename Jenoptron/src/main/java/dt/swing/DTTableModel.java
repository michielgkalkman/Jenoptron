/*
 * Created on Jul 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import jdt.icore.IDecisionTable;
import dt.swing.action.ActionUtil;


/**
 * @author Michiel Kalkman
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DTTableModel extends AbstractTableModel implements PropertyChangeListener {
	private static final Logger logger = Logger.getLogger( DTTableModel.class);
    static final long serialVersionUID = 655521655948428215L;
	private IDecisionTable decisionTable;

	public DTTableModel( final IDecisionTable decisionTable) {
		super();
		this.decisionTable = decisionTable;
		
		// TODO: hoe gaat deze weer weg ?
		decisionTable.addPropertyChangeListener( this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public synchronized int getRowCount() {
		return decisionTable.nrConditions() + decisionTable.nrActions() + 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public synchronized int getColumnCount() {
		final int columnCount;
		if( decisionTable.hasGroups()) {
			columnCount = 2 + decisionTable.getRules().size();
		} else {
			columnCount = 1 + decisionTable.getRules().size();
		}
		logger.debug( "#columns=" + columnCount);
		return columnCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public synchronized Object getValueAt( final int rowIndex, final int columnIndex) {
		Object object;
		try {
			object = ActionUtil.getValue(decisionTable, rowIndex, columnIndex);
		} catch (final Exception e) {
			// Exceptie. Data is blijkbaar veranderd. Gewoon niets terug geven.
			object = "";
		}
		return object;
	}


	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt( final Object aValue, final int rowIndex, final int columnIndex) {
		ActionUtil.setValue(decisionTable, rowIndex, columnIndex, aValue);
	}

	public IDecisionTable getDecisionTable() {
		return decisionTable;
	}

	public void setDecisionTable(final IDecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}

	public void propertyChange(final PropertyChangeEvent evt) {
		logger.debug( "PropertyChangeEvent: " + evt.getPropagationId());
		fireTableStructureChanged();
		fireTableDataChanged();	
	}
}
