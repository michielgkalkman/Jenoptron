package dt.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import jdt.core.binary.BinaryActionValue;
import jdt.icore.IAction;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

import org.apache.log4j.Logger;

import dt.swing.DTTable;

public class DoAction extends AbstractAction {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger( DoAction.class);
	private final DTTable table;

	public DoAction( final DTTable table) {
		super();
		this.table = table;
	}

	@Override
	public void actionPerformed( final ActionEvent actionEvent) {
		final IDecisionTable decisionTable = table.getDecisionTable();
		
		if( table.getSelectedColumnCount() == 0) {
			final int currentSelectedColumn = table.getColumnSelected();
			final int currentSelectedRow = table.getRowSelected();
			if( currentSelectedColumn == 0) {
				ActionUtil.setValue(decisionTable, currentSelectedRow, BinaryActionValue.DO);
			} else if( currentSelectedRow == 0) {
				logger.debug( "Process column " + currentSelectedColumn);
				final IRule rule = decisionTable.getRule( currentSelectedColumn-1);

				for( final IAction action : decisionTable.getActions()) {
					rule.setActionValue( action, BinaryActionValue.DO);
				}
			} else {
				ActionUtil.setValue(decisionTable, currentSelectedRow, currentSelectedColumn, BinaryActionValue.DO);
			}
		} else {
			final int[] selectedColumns = table.getSelectedColumns();
	
			for( int i=0; i<selectedColumns.length; i++) {
				final int selectedColumn = selectedColumns[i];
				if( selectedColumn > 0) {
					logger.debug( "Process column " + selectedColumn);
					final IRule rule = decisionTable.getRule( selectedColumn-1);
	
					for( final IAction action : decisionTable.getActions()) {
						rule.setActionValue( action, BinaryActionValue.DO);
					}
				} else {
					final int[] selectedRows = table.getSelectedRows();
					
					for( int j=0; j<selectedRows.length; j++) {
						ActionUtil.setValue(decisionTable, selectedRows[j], BinaryActionValue.DO);
					}
				}
			}
		}
	}
}
