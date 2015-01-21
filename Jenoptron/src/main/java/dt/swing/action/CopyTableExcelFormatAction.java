/*
 * Created on Aug 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import dt.swing.DTTable;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CopyTableExcelFormatAction extends AbstractAction {
    private static final long serialVersionUID = 761374236975419655L;
	private final DTTable table;
	/**
	 * 
	 */
	public CopyTableExcelFormatAction(final DTTable table) {
		super();
		this.table = table;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent actionEvent) {
		final int[] rows = table.getSelectedRows();
		final int nrCols = table.getColumnCount();
		
		final StringBuffer stringBuffer = new StringBuffer();
		
		for( int row : rows) {
			for( int col = 0; col<nrCols;) {
				stringBuffer.append( table.getValueAt( row, col));
				col++;
				if( col < nrCols) {
					stringBuffer.append( '\t');
				}
			}
			stringBuffer.append( '\n');
		}
		
        final StringSelection stringSelection  = new StringSelection( stringBuffer.toString());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents( stringSelection, stringSelection);
	}
}
